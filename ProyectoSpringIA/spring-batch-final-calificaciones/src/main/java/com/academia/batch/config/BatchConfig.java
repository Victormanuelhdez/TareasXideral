package com.academia.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.processor.EstudianteProcessor;
import com.academia.batch.processor.ReporteEstudianteProcessor;

@Configuration
public class BatchConfig {

	@Bean
	public FlatFileItemReader<Estudiante> estudiantesCsvReader() {
		return new FlatFileItemReaderBuilder<Estudiante>()
				.name("estudiantesCsvReader")
				.resource(new ClassPathResource("estudiantes.csv"))
				.delimited()
				.delimiter(",")
				.names("nombre", "grupo", "nota1", "nota2", "nota3")
				.targetType(Estudiante.class)
				.linesToSkip(1)
				.build();
	}

	@Bean
	public JdbcBatchItemWriter<Estudiante> estudiantesJdbcWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Estudiante>()
				.dataSource(dataSource)
				.sql("""
						INSERT INTO estudiantes_procesados
						(nombre, grupo, nota1, nota2, nota3, promedio)
						VALUES (:nombre, :grupo, :nota1, :nota2, :nota3, :promedio)
						""")
				.beanMapped()
				.build();
	}

	@Bean
	public JdbcCursorItemReader<Estudiante> estudiantesProcesadosReader(DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Estudiante>()
				.name("estudiantesProcesadosReader")
				.dataSource(dataSource)
				.sql("SELECT nombre, grupo, promedio FROM estudiantes_procesados")
				.beanRowMapper(Estudiante.class)
				.build();
	}

	@Bean
	public MongoItemWriter<EstudianteReporte> reporteMongoWriter(MongoTemplate mongoTemplate) {
		return new MongoItemWriterBuilder<EstudianteReporte>()
				.template(mongoTemplate)
				.collection("reportes_estudiantes")
				.build();
	}

	@Bean
	public EstudianteProcessor estudianteProcessor() {
		return new EstudianteProcessor();
	}

	@Bean
	public ReporteEstudianteProcessor reporteEstudianteProcessor() {
		return new ReporteEstudianteProcessor();
	}

	@Bean
	public Step paso1(JobRepository jobRepository,
					  PlatformTransactionManager transactionManager,
					  FlatFileItemReader<Estudiante> estudiantesCsvReader,
					  EstudianteProcessor estudianteProcessor,
					  JdbcBatchItemWriter<Estudiante> estudiantesJdbcWriter) {
		return new StepBuilder("paso1", jobRepository)
				.<Estudiante, Estudiante>chunk(3, transactionManager)
				.reader(estudiantesCsvReader)
				.processor(estudianteProcessor)
				.writer(estudiantesJdbcWriter)
				.build();
	}

	@Bean
	public Step paso2(JobRepository jobRepository,
					  PlatformTransactionManager transactionManager,
					  JdbcCursorItemReader<Estudiante> estudiantesProcesadosReader,
					  ReporteEstudianteProcessor reporteEstudianteProcessor,
					  MongoItemWriter<EstudianteReporte> reporteMongoWriter) {
		return new StepBuilder("paso2", jobRepository)
				.<Estudiante, EstudianteReporte>chunk(3, transactionManager)
				.reader(estudiantesProcesadosReader)
				.processor(reporteEstudianteProcessor)
				.writer(reporteMongoWriter)
				.build();
	}

	@Bean
	public Job procesarCalificacionesJob(JobRepository jobRepository,
										@Qualifier("paso1") Step paso1,
										@Qualifier("paso2") Step paso2) {
		return new JobBuilder("procesarCalificacionesJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(paso1)
				.next(paso2)
				.build();
	}
}
