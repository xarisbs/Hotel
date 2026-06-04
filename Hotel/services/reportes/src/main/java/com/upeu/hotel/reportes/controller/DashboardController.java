package com.upeu.hotel.reportes.controller;

import com.upeu.hotel.reportes.dto.DashboardEjecutivoDTO;
import com.upeu.hotel.reportes.dto.DashboardPrincipalDTO;
import com.upeu.hotel.reportes.dto.ReportesEjecutivosDTO;
import com.upeu.hotel.reportes.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public DashboardPrincipalDTO dashboard() {
        return dashboardService.obtenerDashboardPrincipal();
    }

    @GetMapping("/dashboard/ejecutivo")
    public DashboardEjecutivoDTO dashboardEjecutivo() {
        return dashboardService.obtenerDashboardEjecutivo();
    }

    @GetMapping("/ejecutivos")
    public ReportesEjecutivosDTO reportesEjecutivos() {
        return dashboardService.obtenerReportesEjecutivos();
    }

    @GetMapping("/avanzados/ocupacion")
    public Map<String, Long> ocupacionPorEstado() {
        return dashboardService.obtenerOcupacionPorEstado();
    }

    @GetMapping("/avanzados/ingresos")
    public Map<String, BigDecimal> ingresosTotales() {
        return Map.of("ingresosTotales", dashboardService.obtenerIngresosTotales());
    }

    @GetMapping("/avanzados/ingresos-mes")
    public Map<String, BigDecimal> ingresosMes() {
        return Map.of("ingresosMes", dashboardService.obtenerIngresosMes());
    }
}
