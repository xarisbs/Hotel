package com.upeu.hotel.habitacion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "habitaciones")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero", nullable = false, length = 10, unique = true)
    private String numero;

    @Column(name = "piso", nullable = false)
    private Integer piso;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "id_tipo_habitacion", nullable = false)
    private Integer idTipoHabitacion;

    @Column(name = "precio_por_noche", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioPorNoche;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoHabitacion estado;
}
