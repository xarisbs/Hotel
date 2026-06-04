package com.upeu.hotel.reportes.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardPrincipalDTO {
    private long habitacionesDisponibles;
    private long habitacionesOcupadas;
    private long reservasActivas;
    private BigDecimal ingresosMes;
    private long clientesRegistrados;
}
