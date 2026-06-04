package com.upeu.hotel.reserva.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionDetalleDTO {
    private Integer id;
    private String numero;
    private String estado;
    private BigDecimal precioPorNoche;
    private TipoResumenDTO tipoHabitacion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TipoResumenDTO {
        private Integer capacidadMaxima;
    }
}
