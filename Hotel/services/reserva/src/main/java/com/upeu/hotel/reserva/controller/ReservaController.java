package com.upeu.hotel.reserva.controller;

import com.upeu.hotel.reserva.dto.ActualizarEstadoReservaRequest;
import com.upeu.hotel.reserva.dto.ReservaHistorialDTO;
import com.upeu.hotel.reserva.dto.ReservaRequest;
import com.upeu.hotel.reserva.dto.ReservaResponse;
import com.upeu.hotel.reserva.service.ReservaService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponse create(@Valid @RequestBody ReservaRequest request) {
        return reservaService.create(request);
    }

    @GetMapping
    public List<ReservaResponse> findAll() {
        return reservaService.findAll();
    }

    @GetMapping("/huesped/{idHuesped}")
    public List<ReservaHistorialDTO> findByHuesped(@PathVariable Long idHuesped) {
        return reservaService.findByHuesped(idHuesped);
    }

    @GetMapping("/{id}")
    public ReservaResponse findById(@PathVariable Long id) {
        return reservaService.findById(id);
    }

    @PatchMapping("/{id}/estado")
    public ReservaResponse actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEstadoReservaRequest request) {
        return reservaService.actualizarEstado(id, request.getEstado());
    }
}
