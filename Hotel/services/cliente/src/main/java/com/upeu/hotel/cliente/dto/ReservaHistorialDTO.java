package com.upeu.hotel.cliente.dto;

import lombok.*;

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
    private java.math.BigDecimal total;
    private LocalDateTime fechaCreacion;
}
