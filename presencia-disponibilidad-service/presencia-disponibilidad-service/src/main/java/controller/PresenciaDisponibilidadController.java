package cl.duoc.presenciadisponibilidad.controller;

import cl.duoc.presenciadisponibilidad.model.PresenciaDisponibilidad;
import cl.duoc.presenciadisponibilidad.service.PresenciaDisponibilidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presencia-disponibilidad")
public class PresenciaDisponibilidadController {

    private final PresenciaDisponibilidadService service;

    public PresenciaDisponibilidadController(PresenciaDisponibilidadService service) {
        this.service = service;
    }

    @GetMapping
    public List<PresenciaDisponibilidad> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresenciaDisponibilidad> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PresenciaDisponibilidad crear(
            @RequestBody PresenciaDisponibilidad presenciaDisponibilidad) {
        return service.guardar(presenciaDisponibilidad);
    }

    @PutMapping("/{id}")
    public PresenciaDisponibilidad actualizar(
            @PathVariable Long id,
            @RequestBody PresenciaDisponibilidad presenciaDisponibilidad) {
        return service.actualizar(id, presenciaDisponibilidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}