package com.upeu.hotel.reportes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_ingresos", nullable = false)
    private BigDecimal totalIngresos;

    @Column(length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoReporte tipo;

    @Column(nullable = false)
    private LocalDateTime fecha;
}