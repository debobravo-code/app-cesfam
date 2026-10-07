package cl.duoc.presenciadisponibilidad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "presencia_disponibilidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresenciaDisponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del funcionario es obligatorio")
    private Long funcionarioId;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    private String observacion;
}