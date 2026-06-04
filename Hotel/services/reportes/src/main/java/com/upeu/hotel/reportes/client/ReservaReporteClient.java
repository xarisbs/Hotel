package com.upeu.hotel.reportes.client;

import com.upeu.hotel.reportes.dto.ReservaRemotaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "reserva")
public interface ReservaReporteClient {

    @GetMapping("/api/v1/reservas")
    List<ReservaRemotaDTO> listarReservas();
}
