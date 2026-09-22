package cl.duoc.presenciadisponibilidad.model;

import jakarta.persistence.*;
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

    private Long funcionarioId;

    private String estado;

    private String observacion;
}