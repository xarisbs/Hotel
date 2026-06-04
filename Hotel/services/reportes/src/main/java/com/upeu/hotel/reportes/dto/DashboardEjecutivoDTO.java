package com.upeu.hotel.reportes.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardEjecutivoDTO {

    private long totalHuespedes;
    private long reservasActivas;
    private long reservasPendientes;
    private BigDecimal ingresosTotales;
    private BigDecimal ingresosMes;
    private long totalHabitaciones;
    private Map<String, Long> habitacionesPorEstado;
    private double porcentajeOcupacion;
}
