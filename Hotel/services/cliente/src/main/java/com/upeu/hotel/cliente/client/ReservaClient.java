package com.upeu.hotel.cliente.client;

import com.upeu.hotel.cliente.dto.ReservaHistorialDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "reserva")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/huesped/{idHuesped}")
    List<ReservaHistorialDTO> historialPorHuesped(@PathVariable("idHuesped") Long idHuesped);
}
