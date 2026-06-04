package com.upeu.hotel.habitacion.repository;

import com.upeu.hotel.habitacion.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
}
