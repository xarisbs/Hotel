package com.upeu.hotel.reserva.dto;

import com.upeu.hotel.reserva.entity.EstadoReserva;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponse {

    private Long id;
    private Long idHuesped;
    private String nombreHuesped;
    private Long idHabitacion;
    private String numeroHabitacion;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    private Integer cantidadHuespedes;
    private String observaciones;
    private Integer noches;
    private BigDecimal total;
    private EstadoReserva estado;
    private LocalDateTime fechaCreacion;
}
