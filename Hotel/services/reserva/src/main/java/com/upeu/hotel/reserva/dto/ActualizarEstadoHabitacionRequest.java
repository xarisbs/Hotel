package com.upeu.hotel.reserva.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoHabitacionRequest {

    private String estado;
}
