# spring-batch-final-calificaciones

Proyecto Spring Boot que procesa calificaciones desde un archivo CSV, calcula promedios y mueve los resultados por dos etapas:

1. CSV -> MySQL: se leen estudiantes desde [src/main/resources/estudiantes.csv](src/main/resources/estudiantes.csv), se calcula el promedio y se guardan en la tabla estudiantes_procesados.
2. MySQL -> MongoDB: se leen los estudiantes procesados, se clasifica su estado (APROBADO o REPROBADO) y se guarda el reporte en la colección reportes_estudiantes.

## Tecnologias

- Java 17
- Spring Boot 3.2.2
- Spring Batch
- Spring Data JPA
- Spring Data MongoDB
- MySQL
- MongoDB
- Maven
- JUnit 5

## Requisitos previos

- JDK 17 instalado
- Maven 3.9+ instalado
- MySQL en ejecucion
- MongoDB en ejecucion
- Puerto de la aplicacion libre (8080)

## Configuracion de MySQL y MongoDB

La configuracion activa se encuentra en [src/main/resources/application.properties](src/main/resources/application.properties).

Valores actuales del proyecto:

~~~properties
spring.datasource.url=jdbc:mysql://localhost:3309/academia
spring.datasource.username=alumno
spring.datasource.password=alumno123

spring.data.mongodb.uri=mongodb://root:root123@localhost:27018/academia?authSource=admin

spring.batch.jdbc.initialize-schema=always
spring.batch.job.enabled=false

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
~~~

Ajusta usuario, password, puertos y nombres de base de datos si tu entorno local usa valores distintos.

## Como ejecutar el batch

El job definido es procesarCalificacionesJob en [src/main/java/com/academia/batch/config/BatchConfig.java](src/main/java/com/academia/batch/config/BatchConfig.java).

Como en properties esta spring.batch.job.enabled=false, puedes ejecutar el batch explicitamente al arrancar la app:

~~~bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.enabled=true --spring.batch.job.name=procesarCalificacionesJob"
~~~

Flujo esperado del job:

1. Paso 1: lectura de CSV y escritura en MySQL.
2. Paso 2: lectura desde MySQL y escritura de reportes en MongoDB.

## Como ejecutar la API REST

Levanta la aplicacion sin ejecutar automaticamente jobs batch:

~~~bash
mvn spring-boot:run
~~~

El controlador REST esta en [src/main/java/com/academia/batch/controller/EstudianteController.java](src/main/java/com/academia/batch/controller/EstudianteController.java).

Base URL:

~~~text
http://localhost:8080/api/estudiantes
~~~

## Endpoints disponibles

- GET /api/estudiantes/procesados
	- Devuelve la lista de estudiantes procesados desde MySQL.
- GET /api/estudiantes/reportes
	- Devuelve la lista de reportes desde MongoDB.
- GET /api/estudiantes/aprobados/count
	- Devuelve un mapa con el total de aprobados, por ejemplo:

~~~json
{
	"aprobados": 7
}
~~~

## Ejecutar pruebas

Para correr pruebas unitarias con Maven:

~~~bash
mvn test
~~~

## Resultado esperado

Con el archivo [src/main/resources/estudiantes.csv](src/main/resources/estudiantes.csv):

- 10 estudiantes procesados en total
- 7 aprobados
- 3 reprobados

## Evidencias y prompts

- Evidencias del proyecto: [evidencia](evidencia)
- Registro de prompts del trabajo: [PROMPTS.md](PROMPTS.md)
