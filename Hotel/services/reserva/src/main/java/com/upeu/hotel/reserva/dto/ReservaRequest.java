package com.upeu.hotel.reserva.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequest {

    private Long idHuesped;

    private String nombreHuesped;

    @NotNull
    private Long idHabitacion;

    @NotNull
    private LocalDate fechaCheckIn;

    @NotNull
    private LocalDate fechaCheckOut;

    @NotNull
    @Min(1)
    private Integer cantidadHuespedes;

    private String observaciones;
}
