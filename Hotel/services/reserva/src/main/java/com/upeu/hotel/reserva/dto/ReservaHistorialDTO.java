package com.upeu.hotel.reserva.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaHistorialDTO {
    private Long id;
    private String numeroHabitacion;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    private String estado;
    private BigDecimal total;
    private LocalDateTime fechaCreacion;
}
