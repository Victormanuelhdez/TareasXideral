# Evidencia de uso de GitHub Copilot — Victor Manuel Hernández Hernández

## Prompt 1 — pom.xml

- **Prompt:** Genera un pom.xml para un proyecto Spring Boot 3.2.2 con Java 25 y las dependencias indicadas para Spring Batch, MySQL, MongoDB, Web, JPA y Testing.
- **Modalidad:** Copilot Chat
- **Resultado:** Aceptado.
- **Validación:** Ejecuté `mvn clean compile` y terminó con `BUILD SUCCESS`.

## Prompt 2 — Modelo Estudiante

- **Prompt:** Clase modelo Estudiante con los campos nombre, grupo, nota1, nota2, nota3 y promedio.
- **Modalidad:** Autocompletado.
- **Resultado:** La primera sugerencia generó los campos, getters y setters.
- **Corrección:** Tuve que pedir aparte el constructor vacío y el método `toString()`.
- **Validación:** Ejecuté `mvn clean compile` y terminó con `BUILD SUCCESS`.

## Prompt 3 — EstudianteProcessor

- **Prompt:** Crear un `ItemProcessor<Estudiante, Estudiante>` que calcule el promedio, lo asigne al estudiante, use SLF4J y devuelva el mismo objeto.
- **Modalidad:** Autocompletado + Copilot Chat.
- **Resultado:** Las primeras sugerencias usaron `System.out.println()` en lugar de SLF4J.
- **Corrección:** Rechacé esas sugerencias, pedí explícitamente `Logger` y `LoggerFactory`, corregí el mensaje del log y eliminé una línea sobrante que rompía el código.
- **Validación:** Ejecuté `mvn clean compile` y terminó con `BUILD SUCCESS`.

## Prompt 4 — EstudianteReporte

- **Prompt:** Completar la clase como documento de MongoDB usando `@Document(collection = "reportes_estudiantes")` y `@Id`, con los campos id, nombre, grupo, promedio y estado.
- **Modalidad:** Copilot Chat.
- **Resultado:** Aceptado después de verificar los imports de Spring Data MongoDB, las anotaciones, el constructor vacío, getters, setters y `toString()`.
- **Validación:** Ejecuté `mvn clean compile` y terminó con `BUILD SUCCESS`.

## Prompt 5 — BatchConfig

- **Prompt:** Generar la configuración de Spring Batch 5 con `JobRepository`, `JobBuilder`, `StepBuilder`, dos steps y un job.
- **Modalidad:** Copilot Chat.
- **Resultado:** Copilot generó `BatchConfig` con los readers, processors, writers y el job.
- **Corrección:** Eliminé `@EnableBatchProcessing` y su import para mantener la autoconfiguración de Spring Boot.
- **Validación:** Ejecuté `mvn clean compile` y terminó con `BUILD SUCCESS`.

## Prompt 6 — Corrección de ambigüedad en el Job

- **Prompt:** Corregir únicamente el método `procesarCalificacionesJob` usando `@Qualifier("paso1")` y `@Qualifier("paso2")` para resolver la ambigüedad entre los dos beans de tipo `Step`.
- **Modalidad:** Copilot Chat.
- **Resultado:** Copilot agregó los `@Qualifier` y el import correspondiente.
- **Corrección realizada:** Se resolvió el error donde Spring no sabía qué `Step` inyectar en cada parámetro.
- **Validación:** Ejecuté `mvn spring-boot:run`; `paso1` y `paso2` se ejecutaron y el Job terminó con estado `COMPLETED`.

## Validación del batch

- MySQL contiene 10 registros en `estudiantes_procesados`.
- MongoDB contiene 10 documentos en `reportes_estudiantes`.
- Resultado esperado: 7 aprobados y 3 reprobados.
- Después de validar, cambié `spring.batch.job.enabled=false` para evitar duplicados al trabajar con la API.

## Prompt 7 — EstudianteEntity

- **Prompt:** Completar `EstudianteEntity.java` como entidad JPA para la tabla `estudiantes_procesados`.
- **Modalidad:** Copilot Chat.
- **Resultado:** Copilot generó la entidad con `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, los campos solicitados, constructor vacío, getters, setters y `toString()`.
- **Validación:** Ejecuté `mvn clean compile` y terminó con `BUILD SUCCESS`.

## Prompt 8 — Repositorios JPA y MongoDB

- **Prompt 1:** Convertir `EstudianteRepository.java` en una interfaz de Spring Data JPA que extienda `JpaRepository<EstudianteEntity, Long>`, sin métodos personalizados.
- **Prompt 2:** Convertir `ReporteRepository.java` en una interfaz de Spring Data MongoDB que extienda `MongoRepository<EstudianteReporte, String>`, sin métodos personalizados.
- **Modalidad:** Copilot Chat.
- **Resultado:** Copilot generó ambos repositorios con sus imports y tipos genéricos correctos.
- **Validación:** Ejecuté `mvn clean compile` y terminó con `BUILD SUCCESS`.

## Prompt 9 — Servicio de estudiantes

- **Prompt:** Completar `EstudianteService.java` como servicio de Spring, con inyección por constructor de `EstudianteRepository` y `ReporteRepository`.
- **Métodos solicitados:**
  - `obtenerTodos()`
  - `obtenerReportes()`
  - `contarAprobados()`
- **Resultado:** Copilot generó el servicio con `@Service`, acceso a ambos repositorios y conteo de aprobados mediante streams.
- **Validación:** Ejecuté `mvn clean compile` y terminó con `BUILD SUCCESS`.
- **Evidencia:** `05-estudiante-service-copilot.png`

## Prompt 10 — Controlador REST

- **Prompt:** Completar `EstudianteController.java` como controlador REST de Spring.
- **Endpoints solicitados:**
  - `GET /api/estudiantes/procesados`
  - `GET /api/estudiantes/reportes`
  - `GET /api/estudiantes/aprobados/count`
- **Resultado:** Copilot generó el controlador con `@RestController`, `@RequestMapping`, inyección por constructor y los tres endpoints requeridos.
- **Validación:** Ejecuté `mvn clean compile` y terminó con `BUILD SUCCESS`.

## Prompt 11 — Validación de endpoints REST

- **Validación realizada:**
  - `GET /api/estudiantes/procesados` devolvió 10 estudiantes con HTTP 200.
  - `GET /api/estudiantes/reportes` devolvió 10 reportes con HTTP 200.
  - `GET /api/estudiantes/aprobados/count` devolvió `{"aprobados":7}`.
- **Corrección técnica:** Se cambió el proyecto para usar Java 17, compatible con Spring Boot 3.2.2.
- **Resultado final:** La API REST quedó funcionando correctamente con MySQL y MongoDB.

## Prompt 12 — Pruebas unitarias de EstudianteProcessor

- **Prompt:** Crear pruebas unitarias con JUnit 5 para `EstudianteProcessor`, sin levantar el contexto de Spring.
- **Casos probados:**
  - Notas 80, 75 y 90 producen un promedio de 81.67.
  - Notas 70, 70 y 70 producen un promedio de 70.0.
- **Resultado:** Copilot generó dos pruebas usando `@Test` y `assertEquals` con delta de `0.01`.
- **Validación:** Ejecuté `mvn test`.
- **Resultado final:** 3 pruebas ejecutadas, 0 fallos, 0 errores y `BUILD SUCCESS`.

## Prompt 13 — README del proyecto

- **Prompt:** Generar un `README.md` con la descripción del proyecto, tecnologías, requisitos, configuración, ejecución del batch, API REST, endpoints, pruebas y resultados esperados.
- **Resultado:** Copilot generó la documentación principal del proyecto en formato Markdown.
- **Corrección realizada:** Se ajustó el enlace a `PROMPTS.md` para que apuntara correctamente al archivo ubicado en la misma carpeta.
- **Validación:** Se revisó que el README describiera el flujo CSV → MySQL → MongoDB y los tres endpoints REST.