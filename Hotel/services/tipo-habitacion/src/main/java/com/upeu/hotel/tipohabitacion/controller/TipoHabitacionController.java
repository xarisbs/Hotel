package com.upeu.hotel.tipohabitacion.controller;

import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionRequest;
import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionResponse;
import com.upeu.hotel.tipohabitacion.service.TipoHabitacionService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos-habitacion")
@RequiredArgsConstructor
public class TipoHabitacionController {

    private final TipoHabitacionService tipoHabitacionService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TipoHabitacionResponse> create(
            @Valid @ModelAttribute TipoHabitacionRequest request) {

        TipoHabitacionResponse response = tipoHabitacionService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TipoHabitacionResponse>> findAll() {
        return ResponseEntity.ok(tipoHabitacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoHabitacionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoHabitacionService.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TipoHabitacionResponse> update(
            @PathVariable Long id,
            @Valid @ModelAttribute TipoHabitacionRequest request) {

        return ResponseEntity.ok(tipoHabitacionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoHabitacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}