package com.academia.batch.service;

import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import com.academia.batch.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

	private final EstudianteRepository estudianteRepository;
	private final ReporteRepository reporteRepository;

	public EstudianteService(EstudianteRepository estudianteRepository, ReporteRepository reporteRepository) {
		this.estudianteRepository = estudianteRepository;
		this.reporteRepository = reporteRepository;
	}

	public List<EstudianteEntity> obtenerTodos() {
		return estudianteRepository.findAll();
	}

	public List<EstudianteReporte> obtenerReportes() {
		return reporteRepository.findAll();
	}

	public long contarAprobados() {
		return obtenerReportes().stream()
				.filter(reporte -> "APROBADO".equalsIgnoreCase(reporte.getEstado()))
				.count();
	}
}
