package com.upeu.hotel.tipohabitacion.service;

import java.util.List;

import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionRequest;
import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionResponse;

public interface TipoHabitacionService {

    TipoHabitacionResponse create(TipoHabitacionRequest request);

    List<TipoHabitacionResponse> findAll();

    TipoHabitacionResponse findById(Long id);

    TipoHabitacionResponse update(Long id, TipoHabitacionRequest request);

    void delete(Long id);
}
