package com.upeu.hotel.reserva.service;

import com.upeu.hotel.reserva.dto.ReservaHistorialDTO;
import com.upeu.hotel.reserva.dto.ReservaRequest;
import com.upeu.hotel.reserva.dto.ReservaResponse;
import com.upeu.hotel.reserva.entity.EstadoReserva;

import java.util.List;

public interface ReservaService {

    ReservaResponse create(ReservaRequest request);

    List<ReservaResponse> findAll();

    List<ReservaHistorialDTO> findByHuesped(Long idHuesped);

    ReservaResponse findById(Long id);

    ReservaResponse actualizarEstado(Long id, EstadoReserva estado);
}
