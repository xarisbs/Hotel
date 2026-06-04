package com.upeu.hotel.facturacion.dto;

import com.upeu.hotel.facturacion.entity.EstadoPago;
import com.upeu.hotel.facturacion.entity.MetodoPago;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoDTO {
    private Long id;
    private Long reservaId;
    private String numeroComprobante;
    private BigDecimal monto;
    private MetodoPago metodoPago;
    private EstadoPago estado;
    private LocalDateTime fechaPago;
}
