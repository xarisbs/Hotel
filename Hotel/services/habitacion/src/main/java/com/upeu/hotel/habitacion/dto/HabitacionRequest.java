package com.upeu.hotel.habitacion.dto;

import com.upeu.hotel.habitacion.entity.EstadoHabitacion;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionRequest {

    @NotBlank
    @Size(max = 10)
    private String numero;

    @NotNull
    @Min(1)
    private Integer piso;

    private String descripcion;

    @NotNull
    private Integer idTipoHabitacion;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal precioPorNoche;

    private EstadoHabitacion estado;
}
