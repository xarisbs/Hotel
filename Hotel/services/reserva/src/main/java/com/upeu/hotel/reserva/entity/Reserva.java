package com.upeu.hotel.reserva.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_huesped")
    private Long idHuesped;

    @Column(name = "nombre_huesped", nullable = false, length = 120)
    private String nombreHuesped;

    @Column(name = "id_habitacion", nullable = false)
    private Long idHabitacion;

    @Column(name = "numero_habitacion", nullable = false, length = 10)
    private String numeroHabitacion;

    @Column(name = "fecha_check_in", nullable = false)
    private LocalDate fechaCheckIn;

    @Column(name = "fecha_check_out", nullable = false)
    private LocalDate fechaCheckOut;

    @Column(name = "cantidad_huespedes", nullable = false)
    private Integer cantidadHuespedes;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "noches", nullable = false)
    private Integer noches;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoReserva estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
}
