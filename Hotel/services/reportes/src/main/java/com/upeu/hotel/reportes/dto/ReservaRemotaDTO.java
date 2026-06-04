package com.upeu.hotel.reportes.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRemotaDTO {
    private Long id;
    private String nombreHuesped;
    private String numeroHabitacion;
    private String estado;
    private BigDecimal total;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
}
