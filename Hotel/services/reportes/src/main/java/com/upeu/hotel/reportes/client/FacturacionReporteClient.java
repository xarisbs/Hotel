package com.upeu.hotel.reportes.client;

import com.upeu.hotel.reportes.dto.PagoRemotoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "facturacion")
public interface FacturacionReporteClient {

    @GetMapping("/api/v1/facturacion")
    List<PagoRemotoDTO> listarPagos();
}
