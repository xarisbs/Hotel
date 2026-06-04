package com.upeu.hotel.tipohabitacion.mapper;

import org.junit.jupiter.api.Test;

import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionRequest;
import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionResponse;
import com.upeu.hotel.tipohabitacion.entity.TipoHabitacion;
import com.upeu.hotel.tipohabitacion.mapper.TipoHabitacionMapper;

import static org.assertj.core.api.Assertions.assertThat;

class TipoHabitacionMapperTest {

    private final TipoHabitacionMapper tipoHabitacionMapper = new TipoHabitacionMapper();

    @Test
    void shouldMapRequestToEntity() {
        TipoHabitacionRequest request = TipoHabitacionRequest.builder()
                .nombre("Tecnologia")
                .descripcion("Productos tecnologicos")
                .build();

        TipoHabitacion entity = tipoHabitacionMapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getNombre()).isEqualTo("Tecnologia");
        assertThat(entity.getDescripcion()).isEqualTo("Productos tecnologicos");
    }

    @Test
    void shouldMapEntityToResponse() {
        TipoHabitacion entity = TipoHabitacion.builder()
                .id(1L)
                .nombre("Hogar")
                .descripcion("Productos del hogar")
                .build();

        TipoHabitacionResponse response = tipoHabitacionMapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNombre()).isEqualTo("Hogar");
        assertThat(response.getDescripcion()).isEqualTo("Productos del hogar");
    }

    @Test
    void shouldUpdateEntityFromRequest() {
        TipoHabitacion entity = TipoHabitacion.builder()
                .id(1L)
                .nombre("Anterior")
                .descripcion("Anterior descripcion")
                .build();
        TipoHabitacionRequest request = TipoHabitacionRequest.builder()
                .nombre("Nueva")
                .descripcion("Nueva descripcion")
                .build();

        tipoHabitacionMapper.updateEntityFromRequest(entity, request);

        assertThat(entity.getNombre()).isEqualTo("Nueva");
        assertThat(entity.getDescripcion()).isEqualTo("Nueva descripcion");
    }
}
