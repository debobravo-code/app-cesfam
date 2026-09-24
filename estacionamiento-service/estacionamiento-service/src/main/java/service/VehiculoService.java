
package cl.duoc.estacionamiento.service;

import cl.duoc.estacionamiento.model.Vehiculo;
import cl.duoc.estacionamiento.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    // Registrar un vehículo
    public Vehiculo guardar(Vehiculo vehiculo) {
        if (vehiculoRepository.existsByPatente(vehiculo.getPatente())) {
            throw new IllegalArgumentException("La patente ya está registrada");
        }

        return vehiculoRepository.save(vehiculo);
    }

    // Listar todos los vehículos
    public List<Vehiculo> listar() {
        return vehiculoRepository.findAll();
    }

    // Buscar un vehículo por ID
    public Optional<Vehiculo> buscarPorId(Long id) {
        return vehiculoRepository.findById(id);
    }

    // Buscar un vehículo por patente
    public Optional<Vehiculo> buscarPorPatente(String patente) {
        return vehiculoRepository.findByPatente(patente);
    }

    // Actualizar un vehículo
    public Vehiculo actualizar(Long id, Vehiculo datos) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Vehículo no encontrado"));

        if (!vehiculo.getPatente().equals(datos.getPatente())
                && vehiculoRepository.existsByPatente(datos.getPatente())) {
            throw new IllegalArgumentException("La patente ya está registrada");
        }

        vehiculo.setPatente(datos.getPatente());
        vehiculo.setMarca(datos.getMarca());
        vehiculo.setModelo(datos.getModelo());
        vehiculo.setColor(datos.getColor());
        vehiculo.setFotoUrl(datos.getFotoUrl());
        vehiculo.setFuncionarioId(datos.getFuncionarioId());
        vehiculo.setTelefonoContacto(datos.getTelefonoContacto());

        return vehiculoRepository.save(vehiculo);
    }

    // Eliminar un vehículo
    public void eliminar(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehículo no encontrado");
        }

        vehiculoRepository.deleteById(id);
    }
}