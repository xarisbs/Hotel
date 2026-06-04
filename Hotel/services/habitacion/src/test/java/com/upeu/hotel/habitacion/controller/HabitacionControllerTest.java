package com.upeu.hotel.habitacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upeu.hotel.habitacion.dto.HabitacionRequest;
import com.upeu.hotel.habitacion.dto.HabitacionResponse;
import com.upeu.hotel.habitacion.entity.EstadoHabitacion;
import com.upeu.hotel.habitacion.exception.GlobalExceptionHandler;
import com.upeu.hotel.habitacion.service.HabitacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HabitacionController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class HabitacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HabitacionService habitacionService;

    @Test
    void shouldReturnHabitaciones() throws Exception {
        when(habitacionService.findAll()).thenReturn(List.of(
                HabitacionResponse.builder()
                        .id(1)
                        .numero("101")
                        .descripcion("Vista mar")
                        .idTipoHabitacion(1)
                        .precioPorNoche(new BigDecimal("180"))
                        .estado(EstadoHabitacion.DISPONIBLE)
                        .build()));

        mockMvc.perform(get("/api/v1/habitaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numero").value("101"));
    }

    @Test
    void shouldValidateCreateRequest() throws Exception {
        HabitacionRequest request = HabitacionRequest.builder()
                .numero("")
                .idTipoHabitacion(null)
                .precioPorNoche(null)
                .build();

        mockMvc.perform(post("/api/v1/habitaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
