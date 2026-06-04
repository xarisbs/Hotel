package com.upeu.hotel.tipohabitacion.mapper;

import org.springframework.stereotype.Component;

import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionRequest;
import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionResponse;
import com.upeu.hotel.tipohabitacion.entity.TipoHabitacion;

import java.math.BigDecimal;

@Component
public class TipoHabitacionMapper {

    public TipoHabitacion toEntity(TipoHabitacionRequest request) {
        if (request == null) {
            return null;
        }
        return TipoHabitacion.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .capacidadMaxima(request.getCapacidadMaxima())
                .precioBase(request.getPrecioBase())
                .cantidadCamas(request.getCantidadCamas())
                .build();
    }

    public TipoHabitacionResponse toResponse(TipoHabitacion entity) {
        if (entity == null) {
            return null;
        }
        return TipoHabitacionResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .imagen(entity.getImagen())
                .capacidadMaxima(entity.getCapacidadMaxima())
                .precioBase(entity.getPrecioBase())
                .cantidadCamas(entity.getCantidadCamas())
                .build();
    }

    public void updateEntityFromRequest(TipoHabitacion entity, TipoHabitacionRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        if (request.getCapacidadMaxima() != null) {
            entity.setCapacidadMaxima(request.getCapacidadMaxima());
        }
        if (request.getPrecioBase() != null) {
            entity.setPrecioBase(request.getPrecioBase());
        }
        if (request.getCantidadCamas() != null) {
            entity.setCantidadCamas(request.getCantidadCamas());
        }
    }
}
