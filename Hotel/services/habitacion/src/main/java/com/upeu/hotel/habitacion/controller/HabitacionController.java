package com.upeu.hotel.habitacion.controller;

import com.upeu.hotel.habitacion.dto.ActualizarEstadoHabitacionRequest;
import com.upeu.hotel.habitacion.dto.HabitacionRequest;
import com.upeu.hotel.habitacion.dto.HabitacionResponse;
import com.upeu.hotel.habitacion.service.HabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    @PostMapping
    public ResponseEntity<HabitacionResponse> create(@Valid @RequestBody HabitacionRequest request) {
        HabitacionResponse response = habitacionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<HabitacionResponse>> findAll() {
        return ResponseEntity.ok(habitacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitacionResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(habitacionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitacionResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody HabitacionRequest request) {
        return ResponseEntity.ok(habitacionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        habitacionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> findDetalleById(@PathVariable Integer id) {
        return ResponseEntity.ok(habitacionService.findDetalleById(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<HabitacionResponse> actualizarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody ActualizarEstadoHabitacionRequest request) {
        return ResponseEntity.ok(habitacionService.actualizarEstado(id, request.getEstado()));
    }

    @GetMapping("/estadisticas/estados")
    public ResponseEntity<Map<String, Long>> contarPorEstado() {
        return ResponseEntity.ok(habitacionService.contarPorEstado());
    }

}
