package com.upeu.hotel.facturacion.service;


import com.upeu.hotel.facturacion.dto.PagoDTO;

import java.util.List;

public interface PagoService {

    PagoDTO crear(PagoDTO dto);

    List<PagoDTO> listar();

    PagoDTO obtenerPorId(Long id);

    void eliminar(Long id);
}