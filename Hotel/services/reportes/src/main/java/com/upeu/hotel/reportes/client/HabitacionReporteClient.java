package com.upeu.hotel.reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "habitacion")
public interface HabitacionReporteClient {

    @GetMapping("/api/v1/habitaciones/estadisticas/estados")
    Map<String, Long> contarPorEstado();
}
