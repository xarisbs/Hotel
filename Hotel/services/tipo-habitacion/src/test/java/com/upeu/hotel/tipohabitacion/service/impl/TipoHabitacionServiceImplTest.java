package com.upeu.hotel.tipohabitacion.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionRequest;
import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionResponse;
import com.upeu.hotel.tipohabitacion.entity.TipoHabitacion;
import com.upeu.hotel.tipohabitacion.exception.ResourceNotFoundException;
import com.upeu.hotel.tipohabitacion.mapper.TipoHabitacionMapper;
import com.upeu.hotel.tipohabitacion.repository.TipoHabitacionRepository;
import com.upeu.hotel.tipohabitacion.service.impl.TipoHabitacionServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoHabitacionServiceImplTest {

    @Mock
    private TipoHabitacionRepository tipoHabitacionRepository;

    @Spy
    private TipoHabitacionMapper tipoHabitacionMapper = new TipoHabitacionMapper();

    @InjectMocks
    private TipoHabitacionServiceImpl tipoHabitacionService;

    @Test
    void shouldCreateCategoria() {
        TipoHabitacionRequest request = TipoHabitacionRequest.builder()
                .nombre("Tecnologia")
                .descripcion("Productos tecnologicos")
                .build();
        TipoHabitacion savedEntity = TipoHabitacion.builder()
                .id(1L)
                .nombre("Tecnologia")
                .descripcion("Productos tecnologicos")
                .build();

        when(tipoHabitacionRepository.save(any(TipoHabitacion.class))).thenReturn(savedEntity);

        TipoHabitacionResponse response = tipoHabitacionService.create(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNombre()).isEqualTo("Tecnologia");
    }

    @Test
    void shouldThrowWhenCategoriaNotFound() {
        when(tipoHabitacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tipoHabitacionService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
