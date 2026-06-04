package com.upeu.hotel.reserva.dto;

import com.upeu.hotel.reserva.entity.EstadoReserva;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoReservaRequest {

    @NotNull
    private EstadoReserva estado;
}
