package com.upeu.hotel.reportes.service;

import com.upeu.hotel.reportes.dto.DashboardEjecutivoDTO;
import com.upeu.hotel.reportes.dto.DashboardPrincipalDTO;
import com.upeu.hotel.reportes.dto.ReportesEjecutivosDTO;

import java.math.BigDecimal;
import java.util.Map;

public interface DashboardService {

    DashboardPrincipalDTO obtenerDashboardPrincipal();

    DashboardEjecutivoDTO obtenerDashboardEjecutivo();

    ReportesEjecutivosDTO obtenerReportesEjecutivos();

    Map<String, Long> obtenerOcupacionPorEstado();

    BigDecimal obtenerIngresosTotales();

    BigDecimal obtenerIngresosMes();
}
