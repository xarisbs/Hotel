package com.upeu.hotel.habitacion.dto;

import com.upeu.hotel.habitacion.entity.EstadoHabitacion;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoHabitacionRequest {

    @NotNull
    private EstadoHabitacion estado;
}
