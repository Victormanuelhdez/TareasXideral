package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EstudianteProcessorTest {

	@Test
	void process_debeCalcularPromedio_8167() throws Exception {
		EstudianteProcessor processor = new EstudianteProcessor();
		Estudiante estudiante = new Estudiante();
		estudiante.setNota1(80);
		estudiante.setNota2(75);
		estudiante.setNota3(90);

		Estudiante resultado = processor.process(estudiante);

		assertEquals(81.67, resultado.getPromedio(), 0.01);
	}

	@Test
	void process_debeCalcularPromedio_700() throws Exception {
		EstudianteProcessor processor = new EstudianteProcessor();
		Estudiante estudiante = new Estudiante();
		estudiante.setNota1(70);
		estudiante.setNota2(70);
		estudiante.setNota3(70);

		Estudiante resultado = processor.process(estudiante);

		assertEquals(70.0, resultado.getPromedio(), 0.01);
	}
}
