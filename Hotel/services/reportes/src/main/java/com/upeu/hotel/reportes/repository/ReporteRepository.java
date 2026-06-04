package com.upeu.hotel.reportes.repository;

import com.upeu.hotel.reportes.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
}