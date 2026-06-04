package com.upeu.hotel.cliente.service;

import com.upeu.hotel.cliente.dto.ClienteRequest;
import com.upeu.hotel.cliente.dto.ClienteResponse;
import com.upeu.hotel.cliente.dto.ReservaHistorialDTO;

import java.util.List;

public interface ClienteService {

    ClienteResponse create(ClienteRequest request);

    List<ClienteResponse> findAll();

    List<ClienteResponse> buscar(String q);

    ClienteResponse findById(Long id);

    List<ReservaHistorialDTO> historialReservas(Long id);

    ClienteResponse update(Long id, ClienteRequest request);

    void delete(Long id);
}
