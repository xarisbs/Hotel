package com.upeu.hotel.reportes.mapper;


import com.upeu.hotel.reportes.dto.ReporteDTO;
import com.upeu.hotel.reportes.entity.Reporte;

public class ReporteMapper {

    public static ReporteDTO toDTO(Reporte r) {
        return ReporteDTO.builder()
                .id(r.getId())
                .totalIngresos(r.getTotalIngresos())
                .descripcion(r.getDescripcion())
                .tipo(r.getTipo())
                .fecha(r.getFecha())
                .build();
    }

    public static Reporte toEntity(ReporteDTO dto) {
        return Reporte.builder()
                .id(dto.getId())
                .totalIngresos(dto.getTotalIngresos())
                .descripcion(dto.getDescripcion())
                .tipo(dto.getTipo())
                .fecha(dto.getFecha())
                .build();
    }
}