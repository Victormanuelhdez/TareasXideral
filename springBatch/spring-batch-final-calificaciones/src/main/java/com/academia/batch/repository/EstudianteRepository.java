package com.academia.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {

    // Spring Data crea la consulta automaticamente a partir del nombre del metodo
    List<EstudianteEntity> findByGrupo(String grupo);
}