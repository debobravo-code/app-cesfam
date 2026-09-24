
package cl.duoc.notificaciones.controller;

import cl.duoc.notificaciones.model.Notificacion;
import cl.duoc.notificaciones.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    // Listar todas las notificaciones
    @GetMapping
    public List<Notificacion> listarTodas() {
        return service.listarTodas();
    }

    // Buscar una notificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una notificación
    @PostMapping
    public Notificacion crear(@RequestBody Notificacion notificacion) {
        return service.crear(notificacion);
    }

    // Actualizar una notificación
    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizar(
            @PathVariable Long id,
            @RequestBody Notificacion notificacion) {

        return service.actualizar(id, notificacion)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una notificación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}