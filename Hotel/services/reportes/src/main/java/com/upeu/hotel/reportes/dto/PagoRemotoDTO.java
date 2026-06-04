package com.upeu.hotel.reportes.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoRemotoDTO {
    private Long id;
    private Long reservaId;
    private String numeroComprobante;
    private BigDecimal monto;
    private String metodoPago;
    private String estado;
    private LocalDateTime fechaPago;
}
