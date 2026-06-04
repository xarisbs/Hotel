package com.upeu.hotel.reserva.client;

import com.upeu.hotel.reserva.dto.ActualizarEstadoHabitacionRequest;
import com.upeu.hotel.reserva.dto.HabitacionDTO;
import com.upeu.hotel.reserva.dto.HabitacionDetalleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "habitacion")
public interface HabitacionClient {

    @GetMapping("/api/v1/habitaciones/{id}")
    HabitacionDTO obtenerHabitacion(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/habitaciones/detalle/{id}")
    HabitacionDetalleDTO obtenerDetalle(@PathVariable("id") Integer id);

    @PatchMapping("/api/v1/habitaciones/{id}/estado")
    void actualizarEstado(@PathVariable("id") Integer id, @RequestBody ActualizarEstadoHabitacionRequest request);
}
