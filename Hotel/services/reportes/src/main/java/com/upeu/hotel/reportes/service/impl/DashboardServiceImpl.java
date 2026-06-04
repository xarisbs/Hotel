package com.upeu.hotel.reportes.service.impl;

import com.upeu.hotel.reportes.client.ClienteReporteClient;
import com.upeu.hotel.reportes.client.FacturacionReporteClient;
import com.upeu.hotel.reportes.client.HabitacionReporteClient;
import com.upeu.hotel.reportes.client.ReservaReporteClient;
import com.upeu.hotel.reportes.dto.*;
import com.upeu.hotel.reportes.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private static final List<String> MESES = List.of(
            "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic");

    private final HabitacionReporteClient habitacionClient;
    private final ReservaReporteClient reservaClient;
    private final ClienteReporteClient clienteClient;
    private final FacturacionReporteClient facturacionClient;

    @Override
    public DashboardPrincipalDTO obtenerDashboardPrincipal() {
        Map<String, Long> estados = obtenerOcupacionPorEstado();
        List<ReservaRemotaDTO> reservas = reservaClient.listarReservas();
        long activas = reservas.stream()
                .filter(r -> "CONFIRMADA".equals(r.getEstado()) || "CHECK_IN".equals(r.getEstado()))
                .count();

        return DashboardPrincipalDTO.builder()
                .habitacionesDisponibles(estados.getOrDefault("DISPONIBLE", 0L))
                .habitacionesOcupadas(estados.getOrDefault("OCUPADA", 0L))
                .reservasActivas(activas)
                .ingresosMes(obtenerIngresosMes())
                .clientesRegistrados(clienteClient.listarClientes().size())
                .build();
    }

    @Override
    public DashboardEjecutivoDTO obtenerDashboardEjecutivo() {
        Map<String, Long> estados = obtenerOcupacionPorEstado();
        List<ReservaRemotaDTO> reservas = reservaClient.listarReservas();
        long activas = reservas.stream()
                .filter(r -> "CONFIRMADA".equals(r.getEstado()) || "CHECK_IN".equals(r.getEstado()))
                .count();
        long pendientes = reservas.stream().filter(r -> "PENDIENTE".equals(r.getEstado())).count();
        long totalHabitaciones = estados.values().stream().mapToLong(Long::longValue).sum();
        long ocupadas = estados.getOrDefault("OCUPADA", 0L);
        double ocupacion = totalHabitaciones == 0 ? 0 : (ocupadas * 100.0 / totalHabitaciones);

        return DashboardEjecutivoDTO.builder()
                .totalHuespedes(clienteClient.listarClientes().size())
                .reservasActivas(activas)
                .reservasPendientes(pendientes)
                .ingresosTotales(obtenerIngresosTotales())
                .ingresosMes(obtenerIngresosMes())
                .totalHabitaciones(totalHabitaciones)
                .habitacionesPorEstado(estados)
                .porcentajeOcupacion(Math.round(ocupacion * 100.0) / 100.0)
                .build();
    }

    @Override
    public ReportesEjecutivosDTO obtenerReportesEjecutivos() {
        Map<String, Long> estados = obtenerOcupacionPorEstado();
        List<ReservaRemotaDTO> reservas = reservaClient.listarReservas();
        List<PagoRemotoDTO> pagos = facturacionClient.listarPagos().stream()
                .filter(p -> "PAGADO".equalsIgnoreCase(p.getEstado()) || p.getEstado() == null)
                .toList();

        long totalHabitaciones = estados.values().stream().mapToLong(Long::longValue).sum();
        long ocupadas = estados.getOrDefault("OCUPADA", 0L);
        double tasa = totalHabitaciones == 0 ? 0 : (ocupadas * 100.0 / totalHabitaciones);

        Map<String, Long> reservasPorMes = new LinkedHashMap<>();
        Map<String, BigDecimal> ingresosPorMes = new LinkedHashMap<>();
        for (String mes : MESES) {
            reservasPorMes.put(mes, 0L);
            ingresosPorMes.put(mes, BigDecimal.ZERO);
        }

        for (ReservaRemotaDTO r : reservas) {
            if (r.getFechaCheckIn() == null) {
                continue;
            }
            String mes = MESES.get(r.getFechaCheckIn().getMonthValue() - 1);
            reservasPorMes.merge(mes, 1L, Long::sum);
        }

        for (PagoRemotoDTO p : pagos) {
            LocalDateTime fecha = p.getFechaPago();
            if (fecha == null) {
                continue;
            }
            String mes = MESES.get(fecha.getMonthValue() - 1);
            ingresosPorMes.merge(mes, p.getMonto(), BigDecimal::add);
        }

        Map<String, Long> reservasPorHabitacion = reservas.stream()
                .filter(r -> r.getNumeroHabitacion() != null)
                .collect(Collectors.groupingBy(ReservaRemotaDTO::getNumeroHabitacion, Collectors.counting()));

        List<ReportesEjecutivosDTO.HabitacionTopDTO> topHabitaciones = reservasPorHabitacion.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(e -> ReportesEjecutivosDTO.HabitacionTopDTO.builder()
                        .numeroHabitacion(e.getKey())
                        .totalReservas(e.getValue())
                        .build())
                .toList();

        Map<String, List<ReservaRemotaDTO>> porHuesped = reservas.stream()
                .filter(r -> r.getNombreHuesped() != null)
                .collect(Collectors.groupingBy(ReservaRemotaDTO::getNombreHuesped));

        List<ReportesEjecutivosDTO.ClienteFrecuenteDTO> clientesFrecuentes = porHuesped.entrySet().stream()
                .map(e -> ReportesEjecutivosDTO.ClienteFrecuenteDTO.builder()
                        .nombreHuesped(e.getKey())
                        .totalReservas(e.getValue().size())
                        .montoAcumulado(e.getValue().stream()
                                .map(ReservaRemotaDTO::getTotal)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                        .build())
                .sorted(Comparator.comparingLong(ReportesEjecutivosDTO.ClienteFrecuenteDTO::getTotalReservas).reversed())
                .limit(5)
                .toList();

        return ReportesEjecutivosDTO.builder()
                .ingresosPorMes(ingresosPorMes)
                .reservasPorMes(reservasPorMes)
                .habitacionesMasReservadas(topHabitaciones)
                .clientesFrecuentes(clientesFrecuentes)
                .habitacionesOcupadas(ocupadas)
                .habitacionesDisponibles(estados.getOrDefault("DISPONIBLE", 0L))
                .tasaOcupacion(Math.round(tasa * 100.0) / 100.0)
                .build();
    }

    @Override
    public Map<String, Long> obtenerOcupacionPorEstado() {
        return habitacionClient.contarPorEstado();
    }

    @Override
    public BigDecimal obtenerIngresosTotales() {
        return facturacionClient.listarPagos().stream()
                .filter(p -> p.getMonto() != null)
                .filter(p -> p.getEstado() == null || "PAGADO".equalsIgnoreCase(p.getEstado()))
                .map(PagoRemotoDTO::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal obtenerIngresosMes() {
        int mesActual = LocalDate.now().getMonthValue();
        int anioActual = LocalDate.now().getYear();
        return facturacionClient.listarPagos().stream()
                .filter(p -> p.getFechaPago() != null)
                .filter(p -> p.getFechaPago().getMonthValue() == mesActual)
                .filter(p -> p.getFechaPago().getYear() == anioActual)
                .filter(p -> p.getEstado() == null || "PAGADO".equalsIgnoreCase(p.getEstado()))
                .map(PagoRemotoDTO::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
