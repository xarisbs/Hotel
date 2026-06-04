package com.upeu.hotel.reportes.dto;


import com.upeu.hotel.reportes.entity.TipoReporte;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteDTO {

    private Long id;
    private BigDecimal totalIngresos;
    private String descripcion;
    private TipoReporte tipo;
    private LocalDateTime fecha;
}