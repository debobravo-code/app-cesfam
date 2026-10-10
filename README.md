# App Interna CESFAM

## Descripción del proyecto

Aplicación orientada a la gestión interna de funcionarios de un CESFAM. Su propósito es facilitar la administración de funcionarios, consultar su presencia y disponibilidad, gestionar vehículos asociados al estacionamiento y administrar notificaciones internas.

El sistema está desarrollado utilizando una arquitectura basada en microservicios, donde cada servicio posee una responsabilidad específica y utiliza su propia base de datos.

Proyecto desarrollado para la asignatura **JVY0101 – Java: Diseño y Construcción de Soluciones Nativas en Nube**.

---

## Integrantes

- Débora Bravo — `@debobravo-code`
- Mikela Palma — `@mikpalma-code`

---

## Problema que resuelve

En un CESFAM trabajan funcionarios de distintas áreas y sectores, lo que puede dificultar conocer rápidamente quién es cada funcionario, su estado de disponibilidad o cómo contactar al propietario de un vehículo cuando se requiere gestionar una situación relacionada con el estacionamiento.

La aplicación busca apoyar estos procesos internos mediante servicios independientes para:

- Gestión de funcionarios.
- Presencia y disponibilidad.
- Gestión de estacionamiento.
- Notificaciones internas.

La aplicación **no reemplaza los sistemas clínicos existentes ni administra información clínica de pacientes**. Su alcance corresponde exclusivamente a procesos internos relacionados con funcionarios del establecimiento.

---

## Arquitectura

El proyecto utiliza una **arquitectura de microservicios**, separando las funcionalidades principales en cuatro servicios independientes.

Actualmente se encuentran implementados:

1. Gestión de Funcionarios.
2. Presencia y Disponibilidad.
3. Notificaciones.
4. Gestión de Estacionamiento.

Cada microservicio expone endpoints REST para realizar sus operaciones y utiliza persistencia mediante Spring Data JPA y MySQL.

Los cuatro microservicios se encuentran integrados actualmente en la rama `main` del repositorio.

---

## Microservicios

| Microservicio | Puerto | Base de datos | Endpoint principal |
|---|---:|---|---|
| Gestión de Funcionarios | `8081` | `cesfam_interno_db` | `/api/funcionarios` |
| Presencia y Disponibilidad | `8082` | `presencia_disponibilidad_db` | `/api/presencia-disponibilidad` |
| Notificaciones | `8083` | `notificaciones_db` | `/api/notificaciones` |
| Gestión de Estacionamiento | `8084` | `estacionamiento_db` | `/api/vehiculos` |

---

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- Jakarta Validation
- Lombok
- MySQL
- Maven
- Maven Wrapper
- IntelliJ IDEA
- Postman
- Git
- GitHub

---

## Estructura general del repositorio

```text
app-cesfam/
│
├── CesfamInterno/
│   └── CesfamInterno/
│
├── presencia-disponibilidad-service/
│   └── presencia-disponibilidad-service/
│
├── notificaciones-service/
│   └── notificaciones-service/
│
├── estacionamiento-service/
│   └── estacionamiento-service/
│
├── anexos/
│
└── README.md
```

Cada carpeta interna de los microservicios contiene su respectivo archivo `pom.xml` y Maven Wrapper.

---

# Microservicios implementados

## 1. Gestión de Funcionarios

**Puerto:** `8081`

**Base de datos:** `cesfam_interno_db`

**Endpoint principal:**

```text
/api/funcionarios
```

### Operaciones

- `GET /api/funcionarios` — listar funcionarios.
- `GET /api/funcionarios/{id}` — buscar un funcionario.
- `POST /api/funcionarios` — crear un funcionario.
- `PUT /api/funcionarios/{id}` — actualizar un funcionario.
- `DELETE /api/funcionarios/{id}` — eliminar un funcionario.

El servicio incorpora validaciones para los campos obligatorios y control de RUT duplicado al registrar funcionarios.

También maneja respuestas de error para solicitudes inválidas y registros inexistentes.

---

## 2. Presencia y Disponibilidad

**Puerto:** `8082`

**Base de datos:** `presencia_disponibilidad_db`

**Endpoint principal:**

```text
/api/presencia-disponibilidad
```

### Operaciones

- `GET /api/presencia-disponibilidad` — listar registros.
- `GET /api/presencia-disponibilidad/{id}` — buscar un registro.
- `POST /api/presencia-disponibilidad` — crear un registro.
- `PUT /api/presencia-disponibilidad/{id}` — actualizar un registro.
- `DELETE /api/presencia-disponibilidad/{id}` — eliminar un registro.

El servicio permite administrar el estado de presencia o disponibilidad asociado a un funcionario.

Se aplican validaciones sobre los datos obligatorios, incluyendo el identificador del funcionario y su estado.

---

## 3. Notificaciones

**Puerto:** `8083`

**Base de datos:** `notificaciones_db`

**Endpoint principal:**

```text
/api/notificaciones
```

### Operaciones

- `GET /api/notificaciones` — listar notificaciones.
- `GET /api/notificaciones/{id}` — buscar una notificación.
- `POST /api/notificaciones` — crear una notificación.
- `PUT /api/notificaciones/{id}` — actualizar una notificación.
- `DELETE /api/notificaciones/{id}` — eliminar una notificación.

El servicio permite crear y administrar notificaciones internas.

Se aplican validaciones sobre los datos requeridos y manejo de errores para solicitudes inválidas o registros inexistentes.

---

## 4. Gestión de Estacionamiento

**Puerto:** `8084`

**Base de datos:** `estacionamiento_db`

**Endpoint principal:**

```text
/api/vehiculos
```

### Operaciones

- `GET /api/vehiculos` — listar vehículos.
- `GET /api/vehiculos/{id}` — buscar un vehículo.
- `POST /api/vehiculos` — registrar un vehículo.
- `PUT /api/vehiculos/{id}` — actualizar un vehículo.
- `DELETE /api/vehiculos/{id}` — eliminar un vehículo.

El servicio permite registrar y administrar vehículos asociados a funcionarios.

También incorpora validaciones y manejo de errores para datos inválidos y registros inexistentes.

---

## Arquitectura por capas

Los microservicios están organizados utilizando una arquitectura por capas.

Entre los principales componentes se encuentran:

```text
controller
model
repository
service
```

### Controller

Recibe las solicitudes HTTP y expone los endpoints REST.

### Service

Contiene la lógica de negocio de cada microservicio.

### Repository

Gestiona el acceso y persistencia de los datos mediante Spring Data JPA.

### Model

Contiene las entidades utilizadas para representar y persistir los datos.

Esta separación permite mantener responsabilidades definidas dentro de cada microservicio.

---

## Persistencia con JPA e Hibernate

La persistencia de datos se realiza mediante:

- Spring Data JPA.
- Hibernate.
- MySQL.

Cada microservicio administra su propia información y utiliza su correspondiente base de datos.

Las entidades se encuentran mapeadas mediante anotaciones JPA como:

```java
@Entity
@Id
@GeneratedValue
```

Los repositorios utilizan Spring Data JPA para realizar las operaciones de persistencia.

La separación de las bases de datos permite que cada microservicio sea responsable de sus propios datos.

---

## Referencias entre microservicios

Cada microservicio administra su propia base de datos, manteniendo separada la persistencia de sus entidades.

Por esta razón, cuando un servicio necesita asociar información a un funcionario se utiliza su identificador, por ejemplo `funcionarioId`, como referencia en lugar de establecer relaciones JPA como `@ManyToOne` o `@OneToMany` entre entidades pertenecientes a distintos microservicios.

Este diseño permite mantener la independencia entre los servicios y sus respectivas bases de datos.

Actualmente, almacenar un `funcionarioId` no garantiza por sí solo que dicho funcionario exista en el microservicio de Gestión de Funcionarios.

Como mejora futura, esta validación podría realizarse mediante comunicación entre microservicios. Antes de registrar información asociada a un funcionario, el servicio correspondiente podría consultar al microservicio de Gestión de Funcionarios para comprobar que el identificador exista.

---

## Validaciones y manejo de errores

Los microservicios incorporan validaciones mediante **Jakarta Validation** y manejo de excepciones para responder adecuadamente ante solicitudes incorrectas.

Entre los casos controlados se encuentran:

- Campos obligatorios vacíos o nulos.
- Datos inválidos.
- Registros inexistentes.
- Registros duplicados cuando corresponde.

Entre las respuestas HTTP utilizadas se encuentran:

```text
200 OK
201 Created
400 Bad Request
404 Not Found
```

Los endpoints y distintos casos de error fueron comprobados mediante Postman.

---

# Requisitos para ejecutar el proyecto

Antes de ejecutar la aplicación se requiere tener instalado:

- Git.
- Java.
- MySQL.
- IntelliJ IDEA u otro IDE compatible con proyectos Maven.
- Postman para realizar pruebas de los endpoints.

El proyecto incluye **Maven Wrapper**, por lo que no es necesario disponer de una instalación global de Maven para utilizar los comandos indicados en este documento.

---

# Clonación del proyecto

Para obtener una copia del proyecto:

```powershell
git clone https://github.com/debobravo-code/app-cesfam.git
```

Luego ingresar a la carpeta:

```powershell
cd app-cesfam
```

La rama `main` contiene la versión integrada de los cuatro microservicios.

Para comprobar la rama actual:

```powershell
git branch
```

---

# Configuración de MySQL

Para ejecutar los microservicios se requiere disponer de un servidor MySQL local.

Las bases de datos utilizadas por el proyecto son:

```sql
CREATE DATABASE cesfam_interno_db;
CREATE DATABASE presencia_disponibilidad_db;
CREATE DATABASE notificaciones_db;
CREATE DATABASE estacionamiento_db;
```

Las credenciales de MySQL deben configurarse en el archivo `application.properties` correspondiente a cada microservicio.

Ejemplo general:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/NOMBRE_BASE_DATOS
spring.datasource.username=USUARIO_MYSQL
spring.datasource.password=CONTRASEÑA_MYSQL
```

Las credenciales deben adaptarse a la configuración local del equipo donde se ejecute el proyecto.

---

# Ejecución de los microservicios

Cada microservicio puede ejecutarse de manera independiente.

Los comandos deben ejecutarse desde la carpeta interna correspondiente, donde se encuentra el archivo `pom.xml` y Maven Wrapper.

## Funcionarios

Desde la raíz del repositorio:

```powershell
cd CesfamInterno\CesfamInterno
.\mvnw.cmd spring-boot:run
```

Servicio disponible en:

```text
http://localhost:8081
```

---

## Presencia y Disponibilidad

Desde la raíz del repositorio:

```powershell
cd presencia-disponibilidad-service\presencia-disponibilidad-service
.\mvnw.cmd spring-boot:run
```

Servicio disponible en:

```text
http://localhost:8082
```

---

## Notificaciones

Desde la raíz del repositorio:

```powershell
cd notificaciones-service\notificaciones-service
.\mvnw.cmd spring-boot:run
```

Servicio disponible en:

```text
http://localhost:8083
```

---

## Estacionamiento

Desde la raíz del repositorio:

```powershell
cd estacionamiento-service\estacionamiento-service
.\mvnw.cmd spring-boot:run
```

Servicio disponible en:

```text
http://localhost:8084
```

---

# Ejecución desde IntelliJ IDEA

También es posible ejecutar cada microservicio desde IntelliJ IDEA.

Para ello:

1. Abrir el microservicio como proyecto Maven.
2. Esperar que IntelliJ descargue y sincronice las dependencias.
3. Verificar la configuración de `application.properties`.
4. Ejecutar la clase principal del microservicio.
5. Comprobar en la consola que Spring Boot inició correctamente y que se conectó a MySQL.

Para utilizar los cuatro microservicios simultáneamente, cada uno debe permanecer ejecutándose en su puerto correspondiente.

---

# Verificación con Maven

Cada microservicio puede verificarse mediante Maven Wrapper.

Desde la carpeta correspondiente:

```powershell
.\mvnw.cmd compile
```

Permite comprobar la compilación del código.

```powershell
.\mvnw.cmd test
```

Ejecuta las pruebas configuradas.

```powershell
.\mvnw.cmd package
```

Compila, ejecuta las pruebas y genera el paquete ejecutable correspondiente en:

```text
target/
```

Los cuatro microservicios fueron comprobados desde una clonación limpia del repositorio mediante:

- `mvn test`.
- `mvn package`.
- Generación correcta de sus archivos `.jar`.

---

# Pruebas con Postman

Los endpoints REST pueden probarse mediante Postman.

Ejemplos:

```text
GET http://localhost:8081/api/funcionarios
GET http://localhost:8082/api/presencia-disponibilidad
GET http://localhost:8083/api/notificaciones
GET http://localhost:8084/api/vehiculos
```

Para las operaciones de creación y actualización se deben enviar los datos correspondientes en formato JSON utilizando:

```text
Content-Type: application/json
```

Las pruebas realizadas permiten verificar:

- Operaciones CRUD.
- Persistencia en MySQL.
- Validaciones.
- Manejo de errores.
- Respuestas HTTP.

---

# Seguridad

El proyecto utiliza **Spring Security** en los servicios donde se encuentra configurado.

Durante el desarrollo, Spring Security puede solicitar autenticación para acceder a determinados endpoints dependiendo de la configuración de cada microservicio.

La autenticación mediante **JWT (JSON Web Token)** no forma parte de la implementación actual y puede incorporarse posteriormente como una mejora de seguridad.

---

# Control de versiones

El proyecto utiliza **Git y GitHub** para el control de versiones.

Durante el desarrollo se utilizó una rama de trabajo por integrante y posteriormente los cambios fueron integrados en la rama `main`.

Las principales ramas utilizadas son:

```text
main
DEBORA-BRAVO-FUENTES
MIKELLA-CATALINA-PALMA-CUADRA
```

- `DEBORA-BRAVO-FUENTES`: utilizada para el desarrollo correspondiente a Débora.
- `MIKELLA-CATALINA-PALMA-CUADRA`: utilizada para el desarrollo correspondiente a Mikela.
- `main`: contiene la versión integrada del proyecto.

Este flujo permitió mantener separado el trabajo de ambas integrantes durante el desarrollo y posteriormente integrar los cambios en una versión común.

---

# Diagrama de arquitectura

El diagrama de arquitectura se encuentra disponible en la carpeta:

```text
anexos/
```

También se incluye en este README:

![Diagrama de arquitectura](anexos/imagen%20Diagrama%20ms%20%281%29.png)

---

# Estado actual del proyecto

Actualmente se encuentran implementados e integrados los cuatro microservicios principales:

- Gestión de Funcionarios.
- Presencia y Disponibilidad.
- Notificaciones.
- Gestión de Estacionamiento.

La versión actual incluye:

- Endpoints REST.
- Operaciones CRUD.
- Arquitectura por capas.
- Persistencia mediante MySQL.
- Entidades mapeadas con JPA.
- Persistencia mediante Spring Data JPA / Hibernate.
- Validaciones de datos.
- Manejo de errores.
- Configuración mediante Maven.
- Pruebas de endpoints mediante Postman.
- Verificación mediante Maven.
- Generación de archivos `.jar`.
- Integración de los cuatro microservicios en la rama `main`.
- Documentación para clonación, configuración y ejecución del proyecto.

---

# Posibles mejoras futuras

Como evolución del proyecto se pueden incorporar:

- Validación de `funcionarioId` mediante comunicación con el microservicio de Gestión de Funcionarios.
- Autenticación mediante JWT.
- Comunicación directa entre microservicios.
- Mayor integración funcional entre los servicios.
- Nuevas funcionalidades para la gestión interna del CESFAM.
- Mejoras adicionales de seguridad y despliegue.

---

## Repositorio

Proyecto académico desarrollado por **Débora Bravo y Mikela Palma**.
