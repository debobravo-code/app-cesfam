# App Interna CESFAM

## Descripción del proyecto

Aplicación orientada a facilitar la gestión interna de un CESFAM, permitiendo administrar funcionarios, consultar su disponibilidad, gestionar vehículos y generar notificaciones internas.

El sistema no administra información clínica de pacientes ni reemplaza los sistemas de salud existentes.

Proyecto desarrollado para la asignatura **JVY0101 – Java: Diseño y Construcción de Soluciones Nativas en Nube**.

## Integrantes

- Débora Bravo – @debobravo-code
- Mikela Palma – @mikpalma-code

## Microservicios

El proyecto cuenta con cuatro microservicios independientes, cada uno con su propia base de datos.

| Microservicio | Puerto | Base de datos | Endpoint |
|---|---|---|---|
| Funcionarios | 8081 | cesfam_interno_db | /api/funcionarios |
| Presencia y Disponibilidad | 8082 | presencia_disponibilidad_db | /api/presencia-disponibilidad |
| Notificaciones | 8083 | notificaciones_db | /api/notificaciones |
| Estacionamiento | 8084 | estacionamiento_db | /api/vehiculos |

Los servicios permiten realizar operaciones de creación, consulta, actualización y eliminación de registros, incorporando validaciones y manejo de errores.

## Tecnologías utilizadas

- Java y Spring Boot.
- Spring Data JPA y Hibernate.
- Spring Security.
- MySQL.
- Maven.
- Docker, Docker Compose y Docker Swarm.
- Git, GitHub y GitHub Actions.
- GitHub Container Registry (GHCR).
- AWS EC2, API Gateway, SQS, Lambda y CloudFormation.
- Amazon CloudWatch.
- IntelliJ IDEA y Postman.

## Arquitectura del proyecto

Cada microservicio se encuentra organizado en cuatro capas principales:

- Controller: recibe las solicitudes.
- Service: administra la lógica del servicio.
- Repository: permite acceder a la base de datos.
- Model: representa los datos del sistema.

Esta organización facilita el mantenimiento y permite modificar cada servicio de manera independiente.

## Estructura del repositorio

```text
app-cesfam/
├── CesfamInterno/
├── presencia-disponibilidad-service/
├── notificaciones-service/
├── estacionamiento-service/
├── .github/workflows/
├── infra/
│   ├── aws/
│   └── lambda/
├── anexos/
├── docker-compose.yml
├── docker-stack.yml
└── README.md
```

Cada microservicio contiene su archivo pom.xml, Maven Wrapper y Dockerfile.

## Ejecución local

Para clonar el proyecto:

```bash
git clone https://github.com/debobravo-code/app-cesfam.git
cd app-cesfam
```

Se requiere Java, MySQL y Docker para utilizar las distintas modalidades de ejecución.

Las bases de datos deben estar creadas y las credenciales configuradas según el entorno local.

Cada microservicio puede ejecutarse desde su carpeta interna mediante:

```powershell
.\mvnw.cmd spring-boot:run
```

Para compilar y ejecutar las pruebas:

```powershell
.\mvnw.cmd clean verify
```

También se dispone del archivo docker-compose.yml para ejecutar los servicios en contenedores, previa construcción de las imágenes y configuración de las variables necesarias.

## Docker y Docker Swarm

Cada microservicio cuenta con un Dockerfile que permite generar su propia imagen Docker.

Para el despliegue en AWS se configuraron dos instancias EC2:

- Manager: administra el clúster y sus servicios.
- Worker: permite distribuir la ejecución de los contenedores.

Los microservicios se comunican mediante una red interna de Docker Swarm.

Se utiliza el archivo docker-stack.yml para definir y desplegar los cuatro servicios, considerando sus imágenes, redes, recursos y secretos.

Se comprobó que ambos nodos se encuentran activos y que los cuatro microservicios funcionan correctamente.

## Integración y despliegue continuo (CI/CD)

El proyecto utiliza GitHub Actions para automatizar las siguientes etapas:

1. Compilar y probar los cuatro microservicios.
2. Construir las imágenes Docker.
3. Publicar las imágenes en GitHub Container Registry.
4. Configurar la infraestructura mediante CloudFormation.
5. Actualizar los servicios existentes en Docker Swarm.

El pipeline finalizó correctamente con 10 de 10 trabajos exitosos.

Las actualizaciones se realizan automáticamente al subir cambios a la rama main.

## Infraestructura AWS

Se utilizaron los siguientes servicios:

| Servicio | Función |
|---|---|
| EC2 | Ejecutar el Manager y Worker de Docker Swarm. |
| API Gateway | Controlar el acceso al endpoint publicado. |
| Load Balancer | Conectar API Gateway con los servicios internos. |
| SQS | Recibir mensajes de notificaciones. |
| Lambda | Procesar los mensajes de SQS. |
| CloudFormation | Automatizar la creación de SQS y Lambda. |
| CloudWatch | Consultar registros de ejecución. |

## Comunicación mediante SQS y Lambda

El microservicio de Notificaciones actúa como productor de mensajes y AWS Lambda como consumidor.

El funcionamiento es el siguiente:

1. Se crea una notificación.
2. La información se guarda en MySQL.
3. Se envía un mensaje a Amazon SQS.
4. AWS Lambda recibe y procesa el mensaje.
5. El resultado queda registrado en CloudWatch.

Este proceso permite separar la creación de una notificación de su procesamiento.

## Seguridad

Se implementaron las siguientes medidas:

- Autenticación mediante Spring Security en los servicios configurados.
- Uso de Docker Secrets para almacenar contraseñas.
- GitHub Secrets para proteger las credenciales del pipeline.
- Autenticación AWS IAM en API Gateway.
- Restricción de puertos mediante grupos de seguridad.
- Comunicación interna mediante redes privadas.
- Uso de roles IAM para acceder a los servicios AWS.

Se comprobó que API Gateway rechaza solicitudes sin autorización y permite solicitudes autenticadas.

## Escalabilidad

Se aumentó temporalmente el microservicio de Funcionarios de una a dos réplicas, distribuyéndolas entre el Manager y el Worker.

Se realizaron 100 solicitudes concurrentes en grupos de hasta 10, obteniendo 100 respuestas HTTP 200.

Posteriormente, el servicio volvió a su configuración de una réplica.

## Pruebas realizadas

| Prueba | Resultado |
|---|---|
| Compilación y pruebas Maven | Exitoso |
| Construcción de imágenes Docker | Exitoso |
| Pipeline GitHub Actions | 10/10 |
| Despliegue mediante docker-stack.yml | Exitoso |
| Consulta de los cuatro microservicios | HTTP 200 |
| Escalamiento de Funcionarios | 2 réplicas |
| Prueba de carga | 100 respuestas HTTP 200 |
| API Gateway sin autorización | HTTP 403 |
| API Gateway con autorización | HTTP 200 |
| Envío de mensajes a SQS | Exitoso |
| Procesamiento mediante Lambda | Exitoso |
| Prueba de extremo a extremo (E2E) | Exitosa |

La prueba E2E permitió comprobar la creación de una notificación, su almacenamiento en MySQL, el envío a SQS y su procesamiento mediante Lambda.

## Justificación de decisiones técnicas

- Microservicios: permiten separar las funcionalidades y facilitar el mantenimiento.
- Docker: permite ejecutar los servicios en distintos entornos.
- Docker Swarm: facilita la distribución y escalabilidad de los servicios.
- CI/CD: reduce el trabajo manual y automatiza las actualizaciones.
- SQS y Lambda: permiten procesar mensajes de forma independiente.
- API Gateway: permite controlar el acceso al servicio publicado.
- CloudFormation: facilita la creación repetible de recursos AWS.

Estas decisiones permiten construir una aplicación organizada, escalable y fácil de mantener.

## Limitaciones y mejoras futuras

El proyecto corresponde a una implementación académica que puede continuar mejorándose mediante:

- Incorporación de más nodos Manager para mejorar la disponibilidad.
- Implementación de una base de datos con alta disponibilidad.
- Validación de funcionarios entre microservicios.
- Incorporación de autenticación JWT.
- Mejora del manejo de errores durante el envío de mensajes a SQS.
- Ampliación de las rutas protegidas mediante API Gateway.

Actualmente se utiliza una instancia MySQL con cuatro bases de datos independientes. El despliegue automatizado actualiza servicios previamente configurados en Docker Swarm.

## Diagrama de arquitectura

![Diagrama de arquitectura](anexos/imagen%20Diagrama%20ms%20%281%29.png)

## Evidencias

Las evidencias de configuración, despliegue y pruebas se documentan en la carpeta anexos.

## Control de versiones

El proyecto utiliza Git y GitHub, manteniendo el trabajo de cada integrante en sus respectivas ramas e integrando los cambios en la rama main.

## Estado final

Los cuatro microservicios se encuentran implementados y desplegados en AWS mediante Docker Swarm.

Se comprobó su funcionamiento, escalabilidad, seguridad, procesamiento de mensajes y despliegue automatizado.

Proyecto académico desarrollado por Débora Bravo y Mikela Palma.