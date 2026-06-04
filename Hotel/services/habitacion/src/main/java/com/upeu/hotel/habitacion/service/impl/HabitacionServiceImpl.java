package com.upeu.hotel.habitacion.service.impl;

import com.upeu.hotel.habitacion.client.TipoHabitacionClient;
import com.upeu.hotel.habitacion.dto.HabitacionRequest;
import com.upeu.hotel.habitacion.dto.HabitacionResponse;
import com.upeu.hotel.habitacion.dto.TipoHabitacionDto;
import com.upeu.hotel.habitacion.entity.EstadoHabitacion;
import com.upeu.hotel.habitacion.entity.Habitacion;
import com.upeu.hotel.habitacion.exception.ResourceNotFoundException;
import com.upeu.hotel.habitacion.mapper.HabitacionMapper;
import com.upeu.hotel.habitacion.repository.HabitacionRepository;
import com.upeu.hotel.habitacion.service.HabitacionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;
    private final TipoHabitacionClient tipoHabitacionClient;

    @Override
    @Transactional
    public HabitacionResponse create(HabitacionRequest request) {
        Habitacion saved = habitacionRepository.save(habitacionMapper.toEntity(request));
        return enrichWithTipo(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> findAll() {
        return habitacionRepository.findAll().stream()
                .map(this::enrichWithTipoSafe)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse findById(Integer id) {
        return enrichWithTipo(getHabitacionById(id));
    }

    @Override
    @Transactional
    public HabitacionResponse update(Integer id, HabitacionRequest request) {
        Habitacion habitacion = getHabitacionById(id);
        habitacionMapper.updateEntityFromRequest(habitacion, request);
        return enrichWithTipo(habitacionRepository.save(habitacion));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getHabitacionById(id);
        habitacionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "tipo-habitacion", fallbackMethod = "fallbackDetalle")
    public HabitacionResponse findDetalleById(Integer id) {
        return enrichWithTipo(getHabitacionById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean estaOperativa(Integer id) {
        Habitacion habitacion = getHabitacionById(id);
        return habitacion.getEstado() == EstadoHabitacion.DISPONIBLE;
    }

    @Override
    @Transactional
    public HabitacionResponse actualizarEstado(Integer id, EstadoHabitacion estado) {
        Habitacion habitacion = getHabitacionById(id);
        habitacion.setEstado(estado);
        return habitacionMapper.toResponse(habitacionRepository.save(habitacion));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> contarPorEstado() {
        return habitacionRepository.findAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        h -> h.getEstado().name(),
                        java.util.stream.Collectors.counting()));
    }

    public HabitacionResponse fallbackDetalle(Integer id, Throwable ex) {
        log.warn("[HABITACION] Fallback detalle id {}: {}", id, ex.getMessage());
        return habitacionMapper.toResponse(getHabitacionById(id));
    }

    private Habitacion getHabitacionById(Integer id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación con id " + id + " no encontrada"));
    }

    private HabitacionResponse enrichWithTipo(Habitacion habitacion) {
        TipoHabitacionDto tipo = tipoHabitacionClient.findById(habitacion.getIdTipoHabitacion().longValue());
        HabitacionResponse response = habitacionMapper.toResponse(habitacion);
        response.setTipoHabitacion(tipo);
        return response;
    }

    private HabitacionResponse enrichWithTipoSafe(Habitacion habitacion) {
        HabitacionResponse response = habitacionMapper.toResponse(habitacion);
        try {
            response.setTipoHabitacion(
                    tipoHabitacionClient.findById(habitacion.getIdTipoHabitacion().longValue()));
        } catch (Exception e) {
            log.warn("No se pudo obtener tipo habitación id {}", habitacion.getIdTipoHabitacion());
        }
        return response;
    }
}
