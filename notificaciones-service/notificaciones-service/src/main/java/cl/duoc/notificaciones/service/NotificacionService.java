
package cl.duoc.notificaciones.service;

import cl.duoc.notificaciones.model.Notificacion;
import cl.duoc.notificaciones.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private final NotificacionRepository repository;

    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    // Obtener todas las notificaciones
    public List<Notificacion> listarTodas() {
        return repository.findAll();
    }

    // Buscar una notificación por su ID
    public Optional<Notificacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Crear una notificación
    public Notificacion crear(Notificacion notificacion) {
        notificacion.setId(null);
        notificacion.setFechaCreacion(LocalDateTime.now());
        notificacion.setEstado("PENDIENTE");

        return repository.save(notificacion);
    }

    // Actualizar una notificación existente
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

    // Eliminar una notificación
    public boolean eliminar(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}