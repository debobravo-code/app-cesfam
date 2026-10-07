# App Interna CESFAM

## Arquitectura de Microservicios

Aplicación orientada a la gestión interna de funcionarios de un CESFAM. Su propósito es facilitar la consulta de funcionarios, su presencia y disponibilidad, la gestión de vehículos en el estacionamiento y el envío de notificaciones internas.

El sistema está desarrollado utilizando una arquitectura basada en microservicios, donde cada servicio posee una responsabilidad específica y su propia base de datos.

Proyecto desarrollado para la asignatura **JVY0101 – Java: Diseño y Construcción de Soluciones Nativas en Nube**.

---

## Integrantes

- Débora Bravo — `@debobravo-code`
- Mikela Palma — `@mikpalma-code`

---

## Problema que resuelve

En un CESFAM trabajan funcionarios de distintas áreas y sectores, lo que puede dificultar conocer rápidamente quién es cada funcionario, su estado de disponibilidad o cómo contactar al propietario de un vehículo cuando se requiere gestionar una situación relacionada con el estacionamiento.

La aplicación busca apoyar estos procesos internos mediante servicios independientes para la gestión de funcionarios, presencia y disponibilidad, estacionamiento y notificaciones.

La aplicación **no reemplaza los sistemas clínicos existentes ni administra información clínica de pacientes**. Su alcance corresponde exclusivamente a procesos internos relacionados con funcionarios del establecimiento.

---

## Arquitectura

El proyecto utiliza una **arquitectura de microservicios**, separando las principales funcionalidades de la aplicación en cuatro servicios independientes.

Actualmente se encuentran implementados:

1. Gestión de Funcionarios.
2. Presencia y Disponibilidad.
3. Gestión de Estacionamiento.
4. Notificaciones.

Cada microservicio utiliza una base de datos MySQL independiente y expone servicios REST para realizar sus operaciones.

---

## Microservicios

| Microservicio | Puerto | Base de datos | Responsabilidad |
|---|---:|---|---|
| Gestión de Funcionarios | `8081` | `cesfam_interno_db` | Administración de los datos de los funcionarios |
| Presencia y Disponibilidad | `8082` | `presencia_disponibilidad_db` | Administración del estado y disponibilidad de los funcionarios |
| Notificaciones | `8083` | `notificaciones_db` | Creación y administración de notificaciones internas |
| Gestión de Estacionamiento | `8084` | `estacionamiento_db` | Registro y administración de vehículos asociados a funcionarios |

---

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Jakarta Validation
- Lombok
- MySQL
- Maven
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

---

## Gestión de Funcionarios

**Puerto:** `8081`

**Base de datos:** `cesfam_interno_db`

Endpoint principal:

```text
/api/funcionarios
```

Operaciones implementadas:

- `GET` — listar funcionarios.
- `GET /{id}` — buscar un funcionario.
- `POST` — crear un funcionario.
- `PUT /{id}` — actualizar un funcionario.
- `DELETE /{id}` — eliminar un funcionario.

El servicio incluye validaciones para campos obligatorios y control de RUT duplicado.

También se manejan respuestas de error, incluyendo:

- `400 Bad Request` para datos inválidos o RUT duplicado.
- `404 Not Found` cuando el funcionario solicitado no existe.

---

## Presencia y Disponibilidad

**Puerto:** `8082`

**Base de datos:** `presencia_disponibilidad_db`

Endpoint principal:

```text
/api/presencia-disponibilidad
```

Operaciones implementadas:

- `GET` — listar registros.
- `GET /{id}` — buscar un registro.
- `POST` — crear un registro.
- `PUT /{id}` — actualizar un registro.
- `DELETE /{id}` — eliminar un registro.

El servicio valida datos obligatorios, como el identificador del funcionario y su estado.

Se manejan respuestas como:

- `400 Bad Request` cuando los datos enviados no cumplen las validaciones.
- `404 Not Found` cuando el registro solicitado no existe.

---

## Notificaciones

**Puerto:** `8083`

**Base de datos:** `notificaciones_db`

Endpoint principal:

```text
/api/notificaciones
```

Operaciones implementadas:

- `GET` — listar notificaciones.
- `GET /{id}` — buscar una notificación.
- `POST` — crear una notificación.
- `PUT /{id}` — actualizar una notificación.
- `DELETE /{id}` — eliminar una notificación.

Se aplican validaciones sobre los datos requeridos para crear y modificar notificaciones.

Al crear una notificación, el sistema registra automáticamente su fecha de creación y establece inicialmente su estado correspondiente.

Se manejan respuestas de error como:

- `400 Bad Request` para datos inválidos.
- `404 Not Found` cuando la notificación solicitada no existe.

---

## Gestión de Estacionamiento

**Puerto:** `8084`

**Base de datos:** `estacionamiento_db`

Endpoint principal:

```text
/api/vehiculos
```

Permite administrar los vehículos registrados en el sistema mediante operaciones CRUD.

Operaciones principales:

- `GET` — listar vehículos.
- `GET /{id}` — buscar un vehículo.
- `POST` — registrar un vehículo.
- `PUT /{id}` — actualizar un vehículo.
- `DELETE /{id}` — eliminar un vehículo.

El servicio incorpora validaciones y manejo de errores para los datos ingresados y registros inexistentes.

---

## Validaciones y manejo de errores

Los microservicios incorporan validaciones utilizando **Jakarta Validation** y manejo de excepciones para entregar respuestas HTTP apropiadas.

Entre los casos controlados se encuentran:

- Campos obligatorios vacíos o nulos.
- Datos inválidos.
- Registros inexistentes.
- Registros duplicados cuando corresponde.

Las respuestas HTTP utilizadas incluyen principalmente:

```text
200 OK
201 Created
204 No Content
400 Bad Request
404 Not Found
```

Las operaciones y los casos de error fueron comprobados mediante **Postman**.

---

## Configuración de MySQL

Para ejecutar el proyecto se requiere disponer de un servidor MySQL local.

Las bases de datos utilizadas son:

```text
cesfam_interno_db
presencia_disponibilidad_db
notificaciones_db
estacionamiento_db
```

Las credenciales de acceso a MySQL deben configurarse localmente en el archivo `application.properties` correspondiente a cada microservicio.

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/NOMBRE_BASE_DATOS
spring.datasource.username=USUARIO_MYSQL
spring.datasource.password=CONTRASEÑA_MYSQL
```

Por seguridad, las credenciales deben corresponder a la configuración local del entorno de desarrollo.

---

## Ejecución de los microservicios

Cada microservicio puede ejecutarse de manera independiente.

Desde la carpeta que contiene su archivo `pom.xml`, se puede utilizar Maven Wrapper.

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

También puede ejecutarse directamente desde IntelliJ IDEA utilizando la clase principal de cada microservicio.

Para utilizar simultáneamente los cuatro servicios, deben ejecutarse en sus respectivos puertos:

```text
Funcionarios                  → http://localhost:8081
Presencia y Disponibilidad    → http://localhost:8082
Notificaciones                → http://localhost:8083
Estacionamiento               → http://localhost:8084
```

---

## Verificación con Maven

Para comprobar el correcto funcionamiento de cada microservicio se utilizan los siguientes comandos:

```powershell
.\mvnw.cmd compile
.\mvnw.cmd test
.\mvnw.cmd package
```

Estas verificaciones permiten comprobar:

- Compilación correcta del código.
- Ejecución de las pruebas configuradas.
- Generación correcta del paquete ejecutable del microservicio.

---

## Seguridad

El proyecto utiliza **Spring Security** como base para la seguridad de los servicios.

La implementación de autenticación mediante **JWT (JSON Web Token)** corresponde a una etapa posterior del desarrollo y permitirá fortalecer el proceso de autenticación y autorización de los usuarios.

Por lo tanto, JWT se considera actualmente una **funcionalidad pendiente de implementación**.

---

## Próximas etapas

Entre las siguientes etapas del proyecto se encuentran:

- Implementación de autenticación mediante JWT utilizando Spring Security.
- Integración de los microservicios en una versión compartida del proyecto.
- Integración funcional entre los distintos servicios.
- Fortalecimiento de la comunicación entre los componentes de la arquitectura.

Otros componentes definidos en la arquitectura general podrán incorporarse progresivamente de acuerdo con el alcance y avance del proyecto.

---

## Diagrama de arquitectura

![Diagrama de arquitectura](anexos/imagen%20Diagrama%20ms%20%281%29.png)

El archivo editable del diagrama de arquitectura se encuentra disponible en la carpeta `anexos`.

---

## Estado actual

Actualmente se encuentran implementados los cuatro microservicios principales del proyecto con:

- Operaciones CRUD.
- Persistencia mediante MySQL.
- Validaciones de datos.
- Manejo de errores.
- Pruebas de endpoints mediante Postman.
- Verificación de compilación, pruebas y empaquetado mediante Maven.

El proyecto continuará avanzando con la implementación de seguridad mediante JWT y la integración de los microservicios.
