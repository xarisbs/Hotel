package com.upeu.hotel.tipohabitacion.controller;

import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionRequest;
import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionResponse;
import com.upeu.hotel.tipohabitacion.exception.GlobalExceptionHandler;
import com.upeu.hotel.tipohabitacion.service.TipoHabitacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TipoHabitacionController.class)
@Import(GlobalExceptionHandler.class)
class TipoHabitacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TipoHabitacionService tipoHabitacionService;

    @Test
    void shouldReturnTiposHabitacion() throws Exception {
        when(tipoHabitacionService.findAll()).thenReturn(List.of(
                TipoHabitacionResponse.builder().id(1L).nombre("Estándar").capacidadMaxima(2).build()));

        mockMvc.perform(get("/api/v1/tipos-habitacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Estándar"));
    }

    @Test
    void shouldValidateCreateRequest() throws Exception {
        mockMvc.perform(post("/api/v1/tipos-habitacion")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("nombre", "")
                        .param("capacidadMaxima", "2"))
                .andExpect(status().isBadRequest());
    }
}
