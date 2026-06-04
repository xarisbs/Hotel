package com.upeu.hotel.reportes.metrics;

import com.upeu.hotel.reportes.dto.DashboardEjecutivoDTO;
import com.upeu.hotel.reportes.service.DashboardService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelBusinessMetrics {

    private final DashboardService dashboardService;
    private final MeterRegistry meterRegistry;

    private volatile DashboardEjecutivoDTO snapshot;

    @PostConstruct
    void registerGauges() {
        Gauge.builder("hotel.ocupacion.porcentaje", this, m -> value(m.snapshot != null ? m.snapshot.getPorcentajeOcupacion() : 0))
                .description("Porcentaje de habitaciones ocupadas")
                .register(meterRegistry);
        Gauge.builder("hotel.huespedes.total", this, m -> value(m.snapshot != null ? m.snapshot.getTotalHuespedes() : 0))
                .description("Total de huéspedes registrados")
                .register(meterRegistry);
        Gauge.builder("hotel.reservas.activas", this, m -> value(m.snapshot != null ? m.snapshot.getReservasActivas() : 0))
                .description("Reservas confirmadas o en check-in")
                .register(meterRegistry);
        Gauge.builder("hotel.reservas.pendientes", this, m -> value(m.snapshot != null ? m.snapshot.getReservasPendientes() : 0))
                .description("Reservas pendientes de confirmación")
                .register(meterRegistry);
        Gauge.builder("hotel.ingresos.totales", this, m -> value(m.snapshot != null && m.snapshot.getIngresosTotales() != null
                ? m.snapshot.getIngresosTotales().doubleValue() : 0))
                .description("Ingresos acumulados por facturación")
                .register(meterRegistry);
        Gauge.builder("hotel.habitaciones.total", this, m -> value(m.snapshot != null ? m.snapshot.getTotalHabitaciones() : 0))
                .description("Total de habitaciones del hotel")
                .register(meterRegistry);
    }

    @Scheduled(fixedRate = 30000, initialDelay = 10000)
    void refresh() {
        try {
            snapshot = dashboardService.obtenerDashboardEjecutivo();
        } catch (Exception ex) {
            log.warn("No se pudieron actualizar métricas de negocio: {}", ex.getMessage());
        }
    }

    private double value(double v) {
        return v;
    }

    private double value(long v) {
        return v;
    }
}
