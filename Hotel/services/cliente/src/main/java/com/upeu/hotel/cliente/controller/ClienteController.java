package com.upeu.hotel.cliente.controller;

import com.upeu.hotel.cliente.dto.ClienteRequest;
import com.upeu.hotel.cliente.dto.ClienteResponse;
import com.upeu.hotel.cliente.dto.ReservaHistorialDTO;
import com.upeu.hotel.cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse create(@Valid @RequestBody ClienteRequest request) {
        return clienteService.create(request);
    }

    @GetMapping
    public List<ClienteResponse> findAll(@RequestParam(required = false) String q) {
        if (q != null && !q.isBlank()) {
            return clienteService.buscar(q);
        }
        return clienteService.findAll();
    }

    @GetMapping("/buscar")
    public List<ClienteResponse> buscar(@RequestParam String q) {
        return clienteService.buscar(q);
    }

    @GetMapping("/{id}")
    public ClienteResponse findById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @GetMapping("/{id}/reservas")
    public List<ReservaHistorialDTO> historialReservas(@PathVariable Long id) {
        return clienteService.historialReservas(id);
    }

    @PutMapping("/{id}")
    public ClienteResponse update(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        return clienteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }
}
