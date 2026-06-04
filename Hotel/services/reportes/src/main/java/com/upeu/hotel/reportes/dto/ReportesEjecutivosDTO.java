package com.upeu.hotel.reportes.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportesEjecutivosDTO {
    private Map<String, BigDecimal> ingresosPorMes;
    private Map<String, Long> reservasPorMes;
    private List<HabitacionTopDTO> habitacionesMasReservadas;
    private List<ClienteFrecuenteDTO> clientesFrecuentes;
    private long habitacionesOcupadas;
    private long habitacionesDisponibles;
    private double tasaOcupacion;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HabitacionTopDTO {
        private String numeroHabitacion;
        private long totalReservas;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClienteFrecuenteDTO {
        private String nombreHuesped;
        private long totalReservas;
        private BigDecimal montoAcumulado;
    }
}
