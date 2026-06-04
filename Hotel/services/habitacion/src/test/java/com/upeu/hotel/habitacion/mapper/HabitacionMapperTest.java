package com.upeu.hotel.habitacion.mapper;

import com.upeu.hotel.habitacion.dto.HabitacionRequest;
import com.upeu.hotel.habitacion.dto.HabitacionResponse;
import com.upeu.hotel.habitacion.entity.EstadoHabitacion;
import com.upeu.hotel.habitacion.entity.Habitacion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class HabitacionMapperTest {

    private final HabitacionMapper habitacionMapper = new HabitacionMapper();

    @Test
    void shouldMapRequestToEntity() {
        HabitacionRequest request = HabitacionRequest.builder()
                .numero("201")
                .descripcion("Suite")
                .idTipoHabitacion(2)
                .precioPorNoche(new BigDecimal("250"))
                .estado(EstadoHabitacion.DISPONIBLE)
                .build();

        Habitacion entity = habitacionMapper.toEntity(request);

        assertThat(entity.getNumero()).isEqualTo("201");
        assertThat(entity.getIdTipoHabitacion()).isEqualTo(2);
    }

    @Test
    void shouldMapEntityToResponse() {
        Habitacion entity = Habitacion.builder()
                .id(1)
                .numero("101")
                .descripcion("Estándar")
                .idTipoHabitacion(1)
                .precioPorNoche(new BigDecimal("120"))
                .estado(EstadoHabitacion.DISPONIBLE)
                .build();

        HabitacionResponse response = habitacionMapper.toResponse(entity);

        assertThat(response.getNumero()).isEqualTo("101");
        assertThat(response.getPrecioPorNoche()).isEqualByComparingTo("120");
    }
}
