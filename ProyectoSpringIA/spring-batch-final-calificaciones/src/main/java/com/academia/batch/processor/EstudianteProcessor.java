package com.academia.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.academia.batch.model.Estudiante;

// Processor de Spring Batch que implementa ItemProcessor<Estudiante, Estudiante>.
// En process calcula el promedio de nota1, nota2 y nota3, lo asigna al estudiante,
// registra un log con SLF4J y devuelve el mismo estudiante.

public class EstudianteProcessor implements ItemProcessor<Estudiante, Estudiante> {
    private static final Logger log = LoggerFactory.getLogger(EstudianteProcessor.class);

    @Override
    public Estudiante process(Estudiante estudiante) throws Exception {
        double promedio = (estudiante.getNota1() + estudiante.getNota2() + estudiante.getNota3()) / 3;
        estudiante.setPromedio(promedio);
        log.info("Step 1 - Procesando: {}", estudiante);

        return estudiante;
    }
}

