package com.upeu.hotel.habitacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionDTO {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
}