package com.upeu.hotel.habitacion.mapper;

import com.upeu.hotel.habitacion.dto.HabitacionRequest;
import com.upeu.hotel.habitacion.dto.HabitacionResponse;
import com.upeu.hotel.habitacion.entity.EstadoHabitacion;
import com.upeu.hotel.habitacion.entity.Habitacion;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper {

    public Habitacion toEntity(HabitacionRequest request) {
        if (request == null) {
            return null;
        }
        return Habitacion.builder()
                .numero(request.getNumero())
                .piso(request.getPiso() != null ? request.getPiso() : 1)
                .descripcion(request.getDescripcion())
                .idTipoHabitacion(request.getIdTipoHabitacion())
                .precioPorNoche(request.getPrecioPorNoche())
                .estado(request.getEstado() != null ? request.getEstado() : EstadoHabitacion.DISPONIBLE)
                .build();
    }

    public HabitacionResponse toResponse(Habitacion entity) {
        if (entity == null) {
            return null;
        }
        return HabitacionResponse.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .piso(entity.getPiso())
                .descripcion(entity.getDescripcion())
                .idTipoHabitacion(entity.getIdTipoHabitacion())
                .precioPorNoche(entity.getPrecioPorNoche())
                .estado(entity.getEstado())
                .build();
    }

    public void updateEntityFromRequest(Habitacion entity, HabitacionRequest request) {
        entity.setNumero(request.getNumero());
        if (request.getPiso() != null) {
            entity.setPiso(request.getPiso());
        }
        entity.setDescripcion(request.getDescripcion());
        entity.setIdTipoHabitacion(request.getIdTipoHabitacion());
        entity.setPrecioPorNoche(request.getPrecioPorNoche());
        if (request.getEstado() != null) {
            entity.setEstado(request.getEstado());
        }
    }
}
