package cl.duoc.notificaciones.service;

import cl.duoc.notificaciones.model.Notificacion;
import cl.duoc.notificaciones.repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class NotificacionService {

    private static final Logger logger =
            LoggerFactory.getLogger(NotificacionService.class);

    private final NotificacionRepository repository;
    private final SqsProducerService sqsProducerService;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.enabled:false}")
    private boolean sqsEnabled;

    public NotificacionService(
            NotificacionRepository repository,
            SqsProducerService sqsProducerService,
            ObjectMapper objectMapper) {

        this.repository = repository;
        this.sqsProducerService = sqsProducerService;
        this.objectMapper = objectMapper;
    }

    // Obtener todas las notificaciones
    public List<Notificacion> listarTodas() {
        return repository.findAll();
    }

    // Buscar una notificacion por su ID
    public Optional<Notificacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Crear una notificacion
    public Notificacion crear(Notificacion notificacion) {

        notificacion.setId(null);
        notificacion.setFechaCreacion(LocalDateTime.now());
        notificacion.setEstado("PENDIENTE");

        Notificacion guardada = repository.save(notificacion);

        if (sqsEnabled) {
            enviarNotificacionSqs(guardada);
        }

        return guardada;
    }

    // Enviar notificacion a Amazon SQS
    private void enviarNotificacionSqs(Notificacion notificacion) {

        try {
            Map<String, Object> mensaje = new LinkedHashMap<>();

            mensaje.put("notificacionId", notificacion.getId());
            mensaje.put("funcionario", notificacion.getFuncionarioId());
            mensaje.put("titulo", notificacion.getTitulo());
            mensaje.put("detalle", notificacion.getMensaje());
            mensaje.put("tipo", notificacion.getTipo());
            mensaje.put("estado", notificacion.getEstado());
            mensaje.put(
                    "fechaCreacion",
                    notificacion.getFechaCreacion().toString()
            );

            String mensajeJson =
                    objectMapper.writeValueAsString(mensaje);

            String messageId =
                    sqsProducerService.enviarMensaje(mensajeJson);

            logger.info(
                    "Notificacion {} enviada a SQS. MessageId: {}",
                    notificacion.getId(),
                    messageId
            );

        } catch (Exception e) {

            logger.error(
                    "Error al enviar notificacion {} a SQS",
                    notificacion.getId(),
                    e
            );
        }
    }

    // Actualizar una notificacion existente
    public Optional<Notificacion> actualizar(
            Long id, Notificacion datos) {

        return repository.findById(id).map(notificacion -> {

            notificacion.setTitulo(datos.getTitulo());
            notificacion.setMensaje(datos.getMensaje());
            notificacion.setTipo(datos.getTipo());
            notificacion.setEstado(datos.getEstado());

            return repository.save(notificacion);
        });
    }

    // Eliminar una notificacion
    public boolean eliminar(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}