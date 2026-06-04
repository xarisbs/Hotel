package com.upeu.hotel.facturacion.mapper;

import com.upeu.hotel.facturacion.dto.PagoDTO;
import com.upeu.hotel.facturacion.entity.EstadoPago;
import com.upeu.hotel.facturacion.entity.Pago;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class PagoMapper {

    private static final AtomicLong SECUENCIA = new AtomicLong(System.currentTimeMillis() % 100000);

    public static Pago toEntity(PagoDTO dto) {
        EstadoPago estado = dto.getEstado() != null ? dto.getEstado() : EstadoPago.PAGADO;
        return Pago.builder()
                .reservaId(dto.getReservaId())
                .numeroComprobante(dto.getNumeroComprobante() != null
                        ? dto.getNumeroComprobante()
                        : generarComprobante())
                .monto(dto.getMonto())
                .metodoPago(dto.getMetodoPago())
                .estado(estado)
                .fechaPago(dto.getFechaPago() != null ? dto.getFechaPago() : LocalDateTime.now())
                .build();
    }

    public static PagoDTO toDTO(Pago pago) {
        return PagoDTO.builder()
                .id(pago.getId())
                .reservaId(pago.getReservaId())
                .numeroComprobante(pago.getNumeroComprobante())
                .monto(pago.getMonto())
                .metodoPago(pago.getMetodoPago())
                .estado(pago.getEstado())
                .fechaPago(pago.getFechaPago())
                .build();
    }

    private static String generarComprobante() {
        return "B001-" + String.format("%08d", SECUENCIA.incrementAndGet() % 100000000);
    }
}
