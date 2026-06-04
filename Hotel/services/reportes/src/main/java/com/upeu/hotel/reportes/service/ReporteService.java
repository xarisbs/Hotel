package com.upeu.hotel.reportes.service;

import com.upeu.hotel.reportes.dto.ReporteDTO;

import java.util.List;

public interface ReporteService {

    // CRUD
    ReporteDTO crear(ReporteDTO dto);

    List<ReporteDTO> listar();

    ReporteDTO obtenerPorId(Long id);

    ReporteDTO actualizar(Long id, ReporteDTO dto);

    void eliminar(Long id);
}