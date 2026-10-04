package cl.duoc.estacionamiento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La patente es obligatoria")
    @Size(min = 5, max = 10, message = "La patente debe tener entre 5 y 10 caracteres")
    @Column(nullable = false, unique = true)
    private String patente;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no puede superar los 50 caracteres")
    @Column(nullable = false)
    private String marca;

    @Size(max = 50, message = "El modelo no puede superar los 50 caracteres")
    private String modelo;

    @Size(max = 30, message = "El color no puede superar los 30 caracteres")
    private String color;

    private String fotoUrl;

    @NotNull(message = "El ID del funcionario es obligatorio")
    @Column(nullable = false)
    private Long funcionarioId;

    @Size(max = 20, message = "El teléfono de contacto no puede superar los 20 caracteres")
    private String telefonoContacto;
}