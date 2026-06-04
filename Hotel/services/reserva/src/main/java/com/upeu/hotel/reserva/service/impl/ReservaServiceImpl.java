package com.upeu.hotel.reserva.service.impl;

import com.upeu.hotel.reserva.client.ClienteClient;
import com.upeu.hotel.reserva.client.HabitacionClient;
import com.upeu.hotel.reserva.dto.*;
import com.upeu.hotel.reserva.entity.EstadoReserva;
import com.upeu.hotel.reserva.entity.Reserva;
import com.upeu.hotel.reserva.repository.ReservaRepository;
import com.upeu.hotel.reserva.service.ReservaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaServiceImpl implements ReservaService {

    private static final Set<String> ESTADOS_NO_RESERVABLES = Set.of(
            "OCUPADA", "LIMPIEZA", "MANTENIMIENTO", "FUERA_SERVICIO");

    private final ReservaRepository reservaRepository;
    private final HabitacionClient habitacionClient;
    private final ClienteClient clienteClient;

    @Override
    @Transactional
    @CircuitBreaker(name = "habitacion", fallbackMethod = "fallbackCreate")
    public ReservaResponse create(ReservaRequest request) {
        validarFechas(request.getFechaCheckIn(), request.getFechaCheckOut());

        String nombreHuesped = resolverNombreHuesped(request);
        int cantidadHuespedes = request.getCantidadHuespedes() != null ? request.getCantidadHuespedes() : 1;

        HabitacionDetalleDTO detalle = habitacionClient.obtenerDetalle(request.getIdHabitacion().intValue());
        validarDisponibilidadHabitacion(detalle);
        validarCapacidad(detalle, cantidadHuespedes);

        if (reservaRepository.existeConflicto(
                request.getIdHabitacion(),
                request.getFechaCheckIn(),
                request.getFechaCheckOut())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La habitación ya tiene reserva en las fechas indicadas");
        }

        long noches = ChronoUnit.DAYS.between(request.getFechaCheckIn(), request.getFechaCheckOut());
        BigDecimal total = detalle.getPrecioPorNoche().multiply(BigDecimal.valueOf(noches));

        Reserva reserva = Reserva.builder()
                .idHuesped(request.getIdHuesped())
                .nombreHuesped(nombreHuesped)
                .idHabitacion(request.getIdHabitacion())
                .numeroHabitacion(detalle.getNumero())
                .fechaCheckIn(request.getFechaCheckIn())
                .fechaCheckOut(request.getFechaCheckOut())
                .cantidadHuespedes(cantidadHuespedes)
                .observaciones(request.getObservaciones())
                .noches((int) noches)
                .total(total)
                .estado(EstadoReserva.PENDIENTE)
                .fechaCreacion(LocalDateTime.now())
                .build();

        return toResponse(reservaRepository.save(reserva));
    }

    public ReservaResponse fallbackCreate(ReservaRequest request, Throwable ex) {
        log.warn("Fallback reserva create. Motivo: {}", ex.getMessage());
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Servicio de habitaciones no disponible. Intente más tarde.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponse> findAll() {
        return reservaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaHistorialDTO> findByHuesped(Long idHuesped) {
        return reservaRepository.findByIdHuespedOrderByFechaCreacionDesc(idHuesped).stream()
                .map(this::toHistorial)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponse findById(Long id) {
        return toResponse(getReserva(id));
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "habitacion", fallbackMethod = "fallbackActualizarEstado")
    public ReservaResponse actualizarEstado(Long id, EstadoReserva estado) {
        Reserva reserva = getReserva(id);
        reserva.setEstado(estado);
        Reserva guardada = reservaRepository.save(reserva);
        sincronizarEstadoHabitacion(guardada, estado);
        return toResponse(guardada);
    }

    public ReservaResponse fallbackActualizarEstado(Long id, EstadoReserva estado, Throwable ex) {
        log.warn("Fallback actualizar estado reserva {}. Motivo: {}", id, ex.getMessage());
        Reserva reserva = getReserva(id);
        reserva.setEstado(estado);
        return toResponse(reservaRepository.save(reserva));
    }

    private void validarDisponibilidadHabitacion(HabitacionDetalleDTO habitacion) {
        if (habitacion == null || habitacion.getEstado() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Habitación no encontrada");
        }
        String estado = habitacion.getEstado().toUpperCase();
        if (!"DISPONIBLE".equals(estado)) {
            String mensaje = ESTADOS_NO_RESERVABLES.contains(estado)
                    ? "No se puede reservar: habitación en estado " + estado
                    : "La habitación no está disponible: " + habitacion.getNumero() + " (estado: " + estado + ")";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje);
        }
    }

    private void validarCapacidad(HabitacionDetalleDTO habitacion, int cantidadHuespedes) {
        Integer capacidad = habitacion.getTipoHabitacion() != null
                ? habitacion.getTipoHabitacion().getCapacidadMaxima()
                : null;
        if (capacidad != null && cantidadHuespedes > capacidad) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad de huéspedes (" + cantidadHuespedes + ") supera la capacidad (" + capacidad + ")");
        }
    }

    private void sincronizarEstadoHabitacion(Reserva reserva, EstadoReserva estado) {
        ActualizarEstadoHabitacionRequest request = ActualizarEstadoHabitacionRequest.builder().build();
        switch (estado) {
            case CHECK_IN -> request.setEstado("OCUPADA");
            case CHECK_OUT -> request.setEstado("LIMPIEZA");
            case CANCELADA -> request.setEstado("DISPONIBLE");
            case CONFIRMADA -> request.setEstado("DISPONIBLE");
            default -> { return; }
        }
        habitacionClient.actualizarEstado(reserva.getIdHabitacion().intValue(), request);
    }

    private String resolverNombreHuesped(ReservaRequest request) {
        if (request.getIdHuesped() != null) {
            ClienteDTO cliente = clienteClient.obtenerCliente(request.getIdHuesped());
            return cliente.getNombreCompleto() != null
                    ? cliente.getNombreCompleto()
                    : cliente.getNombre() + " " + cliente.getApellido();
        }
        if (request.getNombreHuesped() == null || request.getNombreHuesped().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar idHuesped o nombreHuesped");
        }
        return request.getNombreHuesped();
    }

    private void validarFechas(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las fechas de check-in y check-out son obligatorias");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha de check-out debe ser posterior al check-in");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El check-in no puede ser anterior a hoy");
        }
    }

    private Reserva getReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reserva no encontrada: " + id));
    }

    private ReservaResponse toResponse(Reserva reserva) {
        return ReservaResponse.builder()
                .id(reserva.getId())
                .idHuesped(reserva.getIdHuesped())
                .nombreHuesped(reserva.getNombreHuesped())
                .idHabitacion(reserva.getIdHabitacion())
                .numeroHabitacion(reserva.getNumeroHabitacion())
                .fechaCheckIn(reserva.getFechaCheckIn())
                .fechaCheckOut(reserva.getFechaCheckOut())
                .cantidadHuespedes(reserva.getCantidadHuespedes())
                .observaciones(reserva.getObservaciones())
                .noches(reserva.getNoches())
                .total(reserva.getTotal())
                .estado(reserva.getEstado())
                .fechaCreacion(reserva.getFechaCreacion())
                .build();
    }

    private ReservaHistorialDTO toHistorial(Reserva reserva) {
        return ReservaHistorialDTO.builder()
                .id(reserva.getId())
                .numeroHabitacion(reserva.getNumeroHabitacion())
                .fechaCheckIn(reserva.getFechaCheckIn())
                .fechaCheckOut(reserva.getFechaCheckOut())
                .estado(reserva.getEstado().name())
                .total(reserva.getTotal())
                .fechaCreacion(reserva.getFechaCreacion())
                .build();
    }
}
