package cl.duoc.presenciadisponibilidad.service;

import cl.duoc.presenciadisponibilidad.model.PresenciaDisponibilidad;
import cl.duoc.presenciadisponibilidad.repository.PresenciaDisponibilidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PresenciaDisponibilidadService {

    private final PresenciaDisponibilidadRepository repository;

    public PresenciaDisponibilidadService(PresenciaDisponibilidadRepository repository) {
        this.repository = repository;
    }

    public List<PresenciaDisponibilidad> listarTodos() {
        return repository.findAll();
    }

    public Optional<PresenciaDisponibilidad> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public PresenciaDisponibilidad guardar(PresenciaDisponibilidad presenciaDisponibilidad) {
        return repository.save(presenciaDisponibilidad);
    }

    public PresenciaDisponibilidad actualizar(
            Long id,
            PresenciaDisponibilidad presenciaDisponibilidad) {

        presenciaDisponibilidad.setId(id);
        return repository.save(presenciaDisponibilidad);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}