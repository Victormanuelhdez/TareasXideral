package com.academia.batch.controller;

import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.service.EstudianteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

	private final EstudianteService estudianteService;

	public EstudianteController(EstudianteService estudianteService) {
		this.estudianteService = estudianteService;
	}

	@GetMapping("/procesados")
	public List<EstudianteEntity> obtenerProcesados() {
		return estudianteService.obtenerTodos();
	}

	@GetMapping("/reportes")
	public List<EstudianteReporte> obtenerReportes() {
		return estudianteService.obtenerReportes();
	}

	@GetMapping("/aprobados/count")
	public Map<String, Long> contarAprobados() {
		return Map.of("aprobados", estudianteService.contarAprobados());
	}
}
