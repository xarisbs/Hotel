package com.upeu.hotel.facturacion.controller;

import com.upeu.hotel.facturacion.dto.PagoDTO;
import com.upeu.hotel.facturacion.service.PagoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facturacion")
@RequiredArgsConstructor
@Tag(name = "Facturación", description = "Pagos y facturación de reservas hoteleras")
public class PagoController {

    private final PagoService service;

    // Crear pago
    @PostMapping
    public ResponseEntity<PagoDTO> crear(@RequestBody PagoDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    // Listar pagos
    @GetMapping
    public ResponseEntity<List<PagoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // Eliminar pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}