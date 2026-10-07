package cl.duoc.presenciadisponibilidad.repository;

import cl.duoc.presenciadisponibilidad.model.PresenciaDisponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresenciaDisponibilidadRepository
        extends JpaRepository<PresenciaDisponibilidad, Long> {

}