package com.upeu.hotel.habitacion.service.impl;

import com.upeu.hotel.habitacion.dto.HabitacionRequest;
import com.upeu.hotel.habitacion.dto.HabitacionResponse;
import com.upeu.hotel.habitacion.dto.TipoHabitacionDto;
import com.upeu.hotel.habitacion.entity.EstadoHabitacion;
import com.upeu.hotel.habitacion.entity.Habitacion;
import com.upeu.hotel.habitacion.exception.ResourceNotFoundException;
import com.upeu.hotel.habitacion.client.TipoHabitacionClient;
import com.upeu.hotel.habitacion.mapper.HabitacionMapper;
import com.upeu.hotel.habitacion.repository.HabitacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceImplTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private TipoHabitacionClient tipoHabitacionClient;

    @Spy
    private HabitacionMapper habitacionMapper = new HabitacionMapper();

    @InjectMocks
    private HabitacionServiceImpl habitacionService;

    @Test
    void shouldCreateHabitacion() {
        HabitacionRequest request = HabitacionRequest.builder()
                .numero("101")
                .descripcion("Vista jardín")
                .idTipoHabitacion(1)
                .precioPorNoche(new BigDecimal("150.00"))
                .estado(EstadoHabitacion.DISPONIBLE)
                .build();

        Habitacion savedEntity = Habitacion.builder()
                .id(1)
                .numero("101")
                .descripcion("Vista jardín")
                .idTipoHabitacion(1)
                .precioPorNoche(new BigDecimal("150.00"))
                .estado(EstadoHabitacion.DISPONIBLE)
                .build();

        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(savedEntity);
        when(tipoHabitacionClient.findById(1L)).thenReturn(
                TipoHabitacionDto.builder().id(1L).nombre("Estándar").capacidadMaxima(2).build());

        HabitacionResponse response = habitacionService.create(request);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNumero()).isEqualTo("101");
        assertThat(response.getTipoHabitacion().getNombre()).isEqualTo("Estándar");
    }

    @Test
    void shouldThrowWhenHabitacionNotFound() {
        when(habitacionRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> habitacionService.findById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
