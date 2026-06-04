package com.upeu.hotel.facturacion.repository;

import com.upeu.hotel.facturacion.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}