
package cl.duoc.notificaciones.repository;

import cl.duoc.notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository
        extends JpaRepository<Notificacion, Long> {
}