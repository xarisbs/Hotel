package com.upeu.hotel.reportes.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoReporte {

    INGRESOS_DIARIO("INGRESOS_DIARIO"),
    INGRESOS_MENSUAL("INGRESOS_MENSUAL"),
    OCUPACION("OCUPACION");

    private final String label;

    TipoReporte(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static TipoReporte from(String value) {
        for (TipoReporte t : values()) {
            if (t.label.equalsIgnoreCase(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de reporte inválido: " + value);
    }
}
