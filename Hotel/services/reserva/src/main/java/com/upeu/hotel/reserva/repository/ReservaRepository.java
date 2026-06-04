package com.upeu.hotel.reserva.repository;

import com.upeu.hotel.reserva.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByIdHuespedOrderByFechaCreacionDesc(Long idHuesped);

    @Query("""
            SELECT COUNT(r) > 0 FROM Reserva r
            WHERE r.idHabitacion = :idHabitacion
              AND r.estado <> com.upeu.hotel.reserva.entity.EstadoReserva.CANCELADA
              AND r.fechaCheckIn < :fechaCheckOut
              AND r.fechaCheckOut > :fechaCheckIn
            """)
    boolean existeConflicto(
            @Param("idHabitacion") Long idHabitacion,
            @Param("fechaCheckIn") java.time.LocalDate fechaCheckIn,
            @Param("fechaCheckOut") java.time.LocalDate fechaCheckOut);
}
