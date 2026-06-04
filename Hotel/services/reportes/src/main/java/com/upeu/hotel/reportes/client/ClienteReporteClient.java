package com.upeu.hotel.reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "cliente")
public interface ClienteReporteClient {

    @GetMapping("/api/v1/clientes")
    List<Object> listarClientes();
}
