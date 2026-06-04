package com.upeu.hotel.reserva.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionDTO {

    private Integer id;
    private String numero;
    private String descripcion;
    private Integer idTipoHabitacion;
    private BigDecimal precioPorNoche;
    private String estado;
}
