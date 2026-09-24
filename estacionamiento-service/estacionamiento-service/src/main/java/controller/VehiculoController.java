
package cl.duoc.estacionamiento.controller;

import cl.duoc.estacionamiento.model.Vehiculo;
import cl.duoc.estacionamiento.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // Registrar un vehículo
    @PostMapping
    public ResponseEntity<Vehiculo> guardar(@RequestBody Vehiculo vehiculo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculoService.guardar(vehiculo));
    }

    // Listar todos los vehículos
    @GetMapping
    public List<Vehiculo> listar() {
        return vehiculoService.listar();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscarPorId(@PathVariable Long id) {
        return vehiculoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar por patente
    @GetMapping("/patente/{patente}")
    public ResponseEntity<Vehiculo> buscarPorPatente(
            @PathVariable String patente) {
        return vehiculoService.buscarPorPatente(patente)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar un vehículo
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(
            @PathVariable Long id,
            @RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(
                vehiculoService.actualizar(id, vehiculo));
    }

    // Eliminar un vehículo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Gestionar errores de validación de negocio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarError(
            IllegalArgumentException ex) {
        HttpStatus estado = ex.getMessage().equals("Vehículo no encontrado")
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(estado).body(ex.getMessage());
    }
}