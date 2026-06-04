package com.upeu.hotel.habitacion.service;

import com.upeu.hotel.habitacion.dto.ActualizarEstadoHabitacionRequest;
import com.upeu.hotel.habitacion.dto.HabitacionRequest;
import com.upeu.hotel.habitacion.dto.HabitacionResponse;
import com.upeu.hotel.habitacion.entity.EstadoHabitacion;

import java.util.List;
import java.util.Map;

public interface HabitacionService {

    HabitacionResponse create(HabitacionRequest request);

    List<HabitacionResponse> findAll();

    HabitacionResponse findById(Integer id);

    HabitacionResponse update(Integer id, HabitacionRequest request);

    void delete(Integer id);

    HabitacionResponse findDetalleById(Integer id);

    boolean estaOperativa(Integer id);

    HabitacionResponse actualizarEstado(Integer id, EstadoHabitacion estado);

    Map<String, Long> contarPorEstado();
}
