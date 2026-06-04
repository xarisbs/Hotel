package com.upeu.hotel.habitacion.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoHabitacionDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer capacidadMaxima;
}
