package com.upeu.hotel.reserva.client;

import com.upeu.hotel.reserva.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente")
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/{id}")
    ClienteDTO obtenerCliente(@PathVariable("id") Long id);
}
