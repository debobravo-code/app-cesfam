# App Interna CESFAM

## Descripción del proyecto

Aplicación orientada a facilitar la gestión interna de un CESFAM, permitiendo administrar funcionarios, consultar su disponibilidad, gestionar vehículos y generar notificaciones internas.

El sistema no administra información clínica de pacientes ni reemplaza los sistemas de salud existentes.

Proyecto desarrollado para la asignatura **JVY0101 – Java: Diseño y Construcción de Soluciones Nativas en Nube**.

## Integrantes

- Débora Bravo – @debobravo-code
- Mikela Palma – @mikpalma-code

## Microservicios

El proyecto cuenta con cuatro microservicios independientes, cada uno con su propia base de datos lógica.

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
- AWS EC2.
- Amazon API Gateway.
- Application Load Balancer.
- Amazon SQS.
- AWS Lambda.
- AWS CloudFormation.
- AWS IAM.
- Amazon CloudWatch.
- IntelliJ IDEA y Postman.

## Arquitectura del proyecto

Cada microservicio se encuentra organizado en cuatro capas principales:

- Controller: recibe las solicitudes.
- Service: administra la lógica del servicio.
- Repository: permite acceder a la base de datos.
- Model: representa los datos del sistema.

Esta organización facilita el mantenimiento y permite modificar cada servicio de manera independiente.

La arquitectura incorpora comunicación mediante API REST, procesamiento asíncrono de mensajes, despliegue distribuido y automatización de infraestructura en la nube.

## Estructura del repositorio

```text
app-cesfam/
├── CesfamInterno/
├── presencia-disponibilidad-service/
├── notificaciones-service/
├── estacionamiento-service/
├── .github/
│   └── workflows/
│       └── main.yml
├── infra/
│   ├── aws/
│   │   └── template.yaml
│   └── lambda/
│       └── lambda_function.py
├── anexos/
│   └── evidencias/
│       ├── 04_CI_CD/
│       ├── 05_CloudFormation/
│       ├── 06_Docker_Swarm/
│       ├── 07_Escalabilidad/
│       ├── 09_API_Gateway/
│       └── 11_Prueba_E2E/
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

Los microservicios se comunican mediante una red overlay denominada cesfam-swarm-network.

Se utiliza el archivo docker-stack.yml para definir los cuatro servicios, considerando:

- Imágenes almacenadas en GHCR.
- Redes internas.
- Límites y reservas de memoria.
- Políticas de reinicio.
- Políticas de actualización y rollback.
- Distribución de réplicas.
- Variables de entorno.
- Docker Secrets.
- Docker Configs.

Se comprobó que ambos nodos se encontraban en estado Ready y Active durante las pruebas, con los cuatro microservicios operativos.

El despliegue requiere la configuración previa de la red externa, los secretos, la configuración de inicio, la base de datos y los permisos de acceso correspondientes.

## Integración y despliegue continuo (CI/CD)

El proyecto utiliza GitHub Actions para automatizar las siguientes etapas:

1. Compilar y probar los cuatro microservicios.
2. Construir las imágenes Docker.
3. Publicar las imágenes en GitHub Container Registry.
4. Validar la plantilla CloudFormation.
5. Desplegar o actualizar la infraestructura AWS.
6. Verificar los recursos desplegados.
7. Actualizar los servicios existentes en Docker Swarm.

Las imágenes se publican utilizando etiquetas asociadas al commit, facilitando la trazabilidad de las versiones desplegadas.

El pipeline también incorpora acceso SSH temporal mediante reglas de seguridad restringidas, eliminando la regla creada al finalizar la ejecución.

### Última ejecución exitosa verificada

| Característica | Resultado |
|---|---|
| Pipeline | CI/CD - App Interna CESFAM |
| Ejecución | #15 |
| Fecha | 26 de noviembre de 2026 |
| Rama | main |
| Commit | 52afbf8 |
| Trabajos exitosos | 10 de 10 |
| Duración | 4 minutos y 1 segundo |
| Estado | Success |

Las actualizaciones se ejecutan automáticamente al subir cambios a la rama main, siempre que el entorno AWS se encuentre disponible y las credenciales sean válidas.

## Infraestructura AWS

La infraestructura se implementó en la región us-east-1 (N. Virginia).

| Servicio | Función |
|---|---|
| EC2 | Ejecutar el Manager y Worker de Docker Swarm. |
| API Gateway | Controlar el acceso a los endpoints publicados. |
| Application Load Balancer | Distribuir solicitudes hacia los servicios internos. |
| VPC Link | Conectar API Gateway con el balanceador privado. |
| SQS | Recibir y almacenar temporalmente mensajes de notificaciones. |
| Lambda | Procesar los mensajes de SQS. |
| CloudFormation | Automatizar la configuración de SQS, Lambda y su desencadenador. |
| IAM | Administrar permisos y autorización de solicitudes. |
| CloudWatch | Consultar registros de ejecución. |

### Infraestructura como código (IaC)

Se utiliza el archivo:

`infra/aws/template.yaml`

La plantilla CloudFormation permite administrar los siguientes recursos:

| Recurso lógico | Tipo |
|---|---|
| ColaNotificaciones | AWS::SQS::Queue |
| FuncionProcesarNotificaciones | AWS::Lambda::Function |
| DesencadenadorSQS | AWS::Lambda::EventSourceMapping |

El stack cesfam-infra-test fue creado y actualizado correctamente.

La ejecución del pipeline verifica automáticamente los recursos desplegados mediante AWS CLI.

## Comunicación mediante SQS y Lambda

El microservicio de Notificaciones actúa como productor de mensajes y AWS Lambda como consumidor.

El funcionamiento es el siguiente:

1. Se recibe una solicitud para crear una notificación.
2. La información se almacena en MySQL.
3. El microservicio publica un mensaje en Amazon SQS.
4. AWS Lambda recibe y procesa el mensaje.
5. El resultado queda registrado en CloudWatch.

### Recursos utilizados

- Cola SQS: cesfam-notificaciones-test.
- Función Lambda: cesfam-procesar-notificaciones-test.
- Runtime de Lambda: Python 3.13.
- Desencadenador: Amazon SQS.
- Estado del desencadenador verificado: Enabled.

El productor utiliza AWS SDK para Java y obtiene las credenciales mediante el rol IAM asociado a la instancia EC2, evitando incorporar credenciales estáticas en el código fuente.

Este proceso permite separar la creación de una notificación de su procesamiento posterior.

## Seguridad

Se implementaron las siguientes medidas:

- Autenticación mediante Spring Security en los servicios configurados.
- Uso de Docker Secrets para administrar contraseñas.
- GitHub Secrets para proteger las credenciales del pipeline.
- Autenticación AWS IAM en API Gateway.
- Solicitudes externas firmadas mediante AWS Signature Version 4.
- Restricción de puertos mediante grupos de seguridad.
- Comunicación mediante redes privadas.
- Application Load Balancer interno.
- VPC Link para la integración privada.
- Uso de roles IAM para acceder a los servicios AWS.
- Acceso SSH temporal durante el despliegue automatizado.

### Rutas protegidas mediante API Gateway

| Método | Ruta | Autorización |
|---|---|---|
| GET | /api/estado | AWS_IAM |
| POST | /api/notificaciones | AWS_IAM |

La ruta de Notificaciones incorpora una segunda capa de autenticación mediante Spring Security.

Para compatibilizar la firma AWS con la autenticación Basic del microservicio, se implementó un filtro que utiliza la cabecera X-Cesfam-Authorization.

Se comprobó que las solicitudes externas sin autorización reciben HTTP 403 y que las solicitudes correctamente autenticadas reciben HTTP 200.

Los puertos publicados de Funcionarios y Notificaciones se restringieron para permitir el acceso desde el balanceador interno, evitando su exposición pública directa.

## Escalabilidad

Se aumentó temporalmente el microservicio de Funcionarios de una a dos réplicas.

Las réplicas fueron distribuidas entre los dos nodos del clúster:

- Una réplica en el Manager.
- Una réplica en el Worker.

Se ejecutaron dos escenarios de carga:

| Escenario | Solicitudes | Concurrencia máxima | Respuestas HTTP 200 | Éxito |
|---|---:|---:|---:|---:|
| Carga moderada | 20 | 5 | 20 | 100% |
| Carga superior | 100 | 10 | 100 | 100% |

Posteriormente, el servicio volvió a su configuración de una réplica y se verificó nuevamente su funcionamiento.

Estas pruebas demuestran la capacidad de aumentar y disminuir réplicas y responder correctamente a solicitudes concurrentes.

No constituyen una prueba de alta disponibilidad ante fallos de nodos ni una medición completa de rendimiento en producción.

## Prueba de extremo a extremo (E2E)

El 26 de noviembre de 2026 se realizó una prueba integral desde un cliente externo, utilizando API Gateway como punto de entrada.

### Flujo de la prueba

```text
Cliente externo
       |
       v
API Gateway - AWS IAM
       |
       v
VPC Link
       |
       v
Application Load Balancer interno
       |
       v
Docker Swarm
       |
       v
Microservicio Notificaciones
       |
       +------> MySQL
       |
       v
Amazon SQS
       |
       v
AWS Lambda
       |
       v
Amazon CloudWatch
```

### Resultados obtenidos

| Validación | Resultado |
|---|---|
| Solicitud externa sin autorización | HTTP 403 |
| Solicitud externa autorizada | HTTP 200 |
| Creación de notificación | ID 4 |
| Persistencia en MySQL | Verificada |
| Publicación del mensaje en SQS | Verificada mediante su procesamiento posterior |
| Procesamiento mediante Lambda | Exitoso |
| Registro en CloudWatch | Verificado |
| Mensajes disponibles en SQS al finalizar | 0 |
| Mensajes en procesamiento al finalizar | 0 |
| Mensajes retrasados al finalizar | 0 |

La notificación utilizada en la prueba correspondió a los siguientes datos:

- Título: Prueba E2E externa CESFAM.
- Funcionario: 1.
- Tipo: ESTACIONAMIENTO.
- Mensaje: Validacion integral API Gateway Docker Swarm SQS Lambda.
- Estado almacenado: PENDIENTE.

Se verificó posteriormente la persistencia de la notificación mediante una consulta autenticada al microservicio.

### Incidencia y recuperación

Durante la prueba se detectó que el desencadenador de SQS asociado a Lambda se encontraba deshabilitado.

El mensaje permaneció almacenado en la cola hasta que se reactivó manualmente el desencadenador.

Después de la reactivación:

1. Lambda procesó el mensaje pendiente.
2. CloudWatch registró la ejecución exitosa.
3. Se comprobó que la cola no presentaba mensajes pendientes.

El procesamiento se produjo aproximadamente 40 minutos después de la creación de la notificación.

Esta situación permitió comprobar la conservación del mensaje durante la interrupción del consumidor y su procesamiento posterior.

No se determinó la causa exacta de la desactivación del desencadenador.

La prueba valida el flujo integral con recuperación manual, pero no demuestra un procesamiento inmediato ni una recuperación completamente automatizada.

## Resumen de pruebas realizadas

| Prueba | Resultado |
|---|---|
| Compilación y pruebas Maven | Exitoso |
| Construcción de imágenes Docker | Exitoso |
| Pipeline GitHub Actions | 10/10 |
| Despliegue mediante docker-stack.yml | Exitoso |
| Consulta de los cuatro microservicios | HTTP 200 |
| Nodos Docker Swarm | Manager y Worker operativos |
| Escalamiento de Funcionarios | 2 réplicas distribuidas |
| Prueba de carga moderada | 20 respuestas HTTP 200 |
| Prueba de carga superior | 100 respuestas HTTP 200 |
| Reducción de réplicas | Exitosa |
| API Gateway sin autorización | HTTP 403 |
| API Gateway con autorización | HTTP 200 |
| Persistencia en MySQL | Verificada |
| Envío de mensajes a SQS | Verificado |
| Procesamiento mediante Lambda | Exitoso |
| Registro en CloudWatch | Verificado |
| Prueba E2E externa | Exitosa con recuperación manual |

## Justificación de decisiones técnicas

### Microservicios

Permiten separar las funcionalidades del sistema, facilitando el desarrollo independiente y el mantenimiento de cada componente.

### Docker

Permite empaquetar las aplicaciones y sus dependencias, favoreciendo la consistencia entre los entornos de desarrollo y despliegue.

### Docker Swarm

Facilita la administración de contenedores distribuidos, el escalamiento horizontal y la actualización de los servicios.

### CI/CD

Reduce las tareas manuales y permite automatizar la compilación, las pruebas, la publicación de imágenes y el despliegue.

### SQS y Lambda

Permiten desacoplar la generación de notificaciones de su procesamiento, conservando temporalmente los mensajes cuando el consumidor no está disponible.

### API Gateway

Centraliza el acceso externo y permite aplicar mecanismos de autorización antes de acceder al backend.

### Application Load Balancer y VPC Link

Permiten conectar API Gateway con los servicios internos sin exponer directamente sus puertos a Internet.

### CloudFormation

Facilita la administración repetible de recursos AWS mediante infraestructura como código.

Estas decisiones favorecen la escalabilidad, mantenibilidad, seguridad y automatización del sistema.

## Limitaciones y mejoras futuras

El proyecto corresponde a una implementación académica que puede continuar mejorándose mediante:

- Incorporación de tres o más nodos Manager para mejorar la tolerancia a fallos.
- Distribución de infraestructura entre distintas zonas de disponibilidad.
- Implementación de una base de datos con alta disponibilidad.
- Validación de funcionarios entre microservicios.
- Incorporación de autenticación JWT.
- Ampliación de las rutas protegidas mediante API Gateway.
- Implementación de HTTPS en las comunicaciones internas.
- Aplicación de permisos IAM de mínimo privilegio.
- Incorporación de mecanismos de reintento y patrón Transactional Outbox.
- Monitoreo del estado del desencadenador SQS-Lambda.
- Automatización de la recuperación ante fallos.
- Implementación de pruebas de rendimiento más completas.
- Automatización del aprovisionamiento completo de la infraestructura.

Actualmente se utiliza una instancia MySQL con cuatro bases de datos lógicas independientes, por lo que constituye un punto único de fallo.

El clúster cuenta con un Manager y un Worker, sin alta disponibilidad del plano de control.

El pipeline actualiza servicios previamente configurados en Docker Swarm.

CloudFormation automatiza SQS, Lambda y su desencadenador, pero no la totalidad de la infraestructura AWS.

La función Lambda procesa los mensajes y registra su contenido en CloudWatch. No realiza actualmente una entrega externa de notificaciones ni modifica su estado en MySQL.

El entorno AWS Academy utiliza credenciales temporales y puede detenerse al finalizar una sesión. Por ello, los recursos y servicios deben verificarse al reiniciar el laboratorio.

## Diagrama de arquitectura

![Diagrama de arquitectura](anexos/imagen%20Diagrama%20ms%20%281%29.png)

El diagrama original se complementa con el flujo E2E y la descripción de infraestructura incluidos en este documento.

## Evidencias

Se recopilaron 18 evidencias gráficas, organizadas según los componentes y pruebas del proyecto.

### 1. Integración y despliegue continuo

- [13 - Pipeline exitoso](anexos/evidencias/04_CI_CD/13_Pipeline_Exitoso.png)
- [14 - Automatización de infraestructura AWS](anexos/evidencias/04_CI_CD/14_Automatizacion_Infraestructura_AWS.png)

### 2. Infraestructura como código

- [15 - Stack CloudFormation](anexos/evidencias/05_CloudFormation/15_Stack_CloudFormation.png)
- [16 - Recursos desplegados](anexos/evidencias/05_CloudFormation/16_Recursos_Desplegados.png)

### 3. Docker Swarm

- [01 - Instancias EC2 Manager y Worker](anexos/evidencias/06_Docker_Swarm/01_EC2_Manager_Worker.png)
- [02 - Nodos y servicios](anexos/evidencias/06_Docker_Swarm/02_Nodos_y_Servicios.png)
- [03 - Distribución de microservicios](anexos/evidencias/06_Docker_Swarm/03_Distribucion_Microservicios.png)

### 4. Escalabilidad

- [04 - Aumento de réplicas](anexos/evidencias/07_Escalabilidad/04_Aumento_Replicas.png)
- [05 - Pruebas de carga](anexos/evidencias/07_Escalabilidad/05_Pruebas_de_Carga.png)
- [06 - Disminución de réplicas](anexos/evidencias/07_Escalabilidad/06_Disminucion_Replicas.png)

### 5. Seguridad API Gateway

- [07 - Load Balancer Notificaciones](anexos/evidencias/09_API_Gateway/07_Load_Balancer_Notificaciones.png)
- [08 - Petición sin autorización](anexos/evidencias/09_API_Gateway/08_Peticion_Sin_Autorizacion.png)
- [17 - Autorización IAM](anexos/evidencias/09_API_Gateway/17_Autorizacion_IAM.png)

### 6. Prueba E2E e integración asíncrona

- [09 - Petición externa autorizada](anexos/evidencias/11_Prueba_E2E/09_Peticion_Externa_Autorizada.png)
- [10 - Procesamiento Lambda y CloudWatch](anexos/evidencias/11_Prueba_E2E/10_E2E_Procesamiento_Lambda_CloudWatch.png)
- [11 - Persistencia MySQL](anexos/evidencias/11_Prueba_E2E/11_E2E_Persistencia_MySQL.png)
- [12 - Cola SQS vacía](anexos/evidencias/11_Prueba_E2E/12_E2E_Cola_SQS_Vacia.png)
- [18 - Desencadenador SQS-Lambda habilitado](anexos/evidencias/11_Prueba_E2E/18_Desencadenador_SQS_Lambda.png)

## Control de versiones

El proyecto utiliza Git y GitHub, manteniendo el trabajo de cada integrante en sus respectivas ramas e integrando los cambios en la rama main.

El historial de commits permite identificar las contribuciones, modificaciones y avances realizados durante el desarrollo.

## Estado final

Los cuatro microservicios fueron implementados y desplegados en AWS mediante Docker Swarm.

Durante las pruebas se comprobó su funcionamiento, escalabilidad, seguridad, persistencia de datos, procesamiento asíncrono de mensajes y despliegue automatizado.

Se dispone de documentación técnica y evidencias gráficas de los resultados obtenidos.

La disponibilidad posterior de los servicios depende del estado del laboratorio AWS Academy.

Proyecto académico desarrollado por Débora Bravo y Mikela Palma.