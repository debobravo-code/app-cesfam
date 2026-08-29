# app-cesfam
# App Interna CESFAM — Arquitectura de Microservicios

Aplicación móvil para funcionarios de un CESFAM, que permite consultar disponibilidad de compañeros, gestionar el estacionamiento y enviar notificaciones internas.

Trabajo del curso EA1 · JVY0101 — Java: Diseño y Construcción de Soluciones Nativas en Nube.

## Integrantes

- Débora — @debobravo-code
- Mikela — @mikpalma-code

## Contenido del repositorio

- `README.md`: presentación general del proyecto.
- Documento Word: contexto y documentación del proyecto.
- `anexos/`: archivos asociados al proyecto, incluyendo el diagrama de arquitectura en formato imagen y editable.

## Problema que resuelve

En un CESFAM trabajan muchas personas de distintas áreas, lo que dificulta saber quién es cada funcionario, si está disponible y cómo contactar al dueño de un vehículo que bloquea una salida.

La aplicación no reemplaza los sistemas clínicos existentes ni almacena información de pacientes; se enfoca exclusivamente en la gestión interna de los funcionarios.

## Arquitectura

Se optó por una arquitectura de microservicios. El sistema se divide en servicios independientes, cada uno responsable de un dominio específico y con su propia base de datos.

### Flujo general

1. El usuario se identifica de forma segura.
2. Las solicitudes pasan por una entrada única (API Gateway), que las dirige al microservicio correspondiente.
3. Los microservicios se localizan entre sí mediante Service Registry.
4. Los servicios utilizan REST/HTTPS para consultas que requieren respuesta inmediata y eventos mediante una cola de mensajes (Event Bus) para comunicaciones asincrónicas.
5. Un módulo de monitoreo supervisa la disponibilidad, tiempos de respuesta, errores y uso de recursos.

## Microservicios

| Microservicio | Responsabilidad |
| --- | --- |
| Gestión de Funcionarios | Perfil, cargo, área, sector, horario habitual y contacto laboral |
| Presencia y Disponibilidad | Estado actual del funcionario y estados especiales |
| Gestión de Estacionamiento | Registro de vehículos y solicitudes cuando un vehículo bloquea una salida |
| Notificaciones | Envío de avisos a funcionarios y registro de historial |

## Seguridad y disponibilidad

- Comunicaciones mediante HTTPS.
- Autenticación mediante JWT.
- Autorización por roles: Funcionario y Administrador.
- API Gateway como punto de entrada a los servicios.
- Service Registry para el descubrimiento de servicios.
- Circuit Breaker en las dependencias síncronas que pueden fallar.
- Event Bus para comunicación asincrónica entre microservicios.
- Monitoreo de disponibilidad, tiempos de respuesta, errores y uso de recursos.

## Diagrama de arquitectura

![Diagrama de arquitectura](anexos/imagen%20Diagrama%20ms%20%281%29.png)
El archivo editable del diagrama de arquitectura se encuentra en la carpeta `anexos`.

## Documentación

El documento Word incluido en el repositorio contiene el contexto, alcances, responsabilidades, funciones e interacciones de los microservicios del proyecto.
