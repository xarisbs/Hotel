package com.upeu.hotel.habitacion.dto;

import com.upeu.hotel.habitacion.entity.EstadoHabitacion;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionResponse {

    private Integer id;
    private String numero;
    private Integer piso;
    private String descripcion;
    private Integer idTipoHabitacion;
    private BigDecimal precioPorNoche;
    private EstadoHabitacion estado;
    private TipoHabitacionDto tipoHabitacion;
}
