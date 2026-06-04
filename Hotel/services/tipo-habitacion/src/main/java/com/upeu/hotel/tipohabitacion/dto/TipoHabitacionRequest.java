package com.upeu.hotel.tipohabitacion.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoHabitacionRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String descripcion;

    private MultipartFile imagen;

    @NotNull(message = "La capacidad máxima es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacidadMaxima;

    @NotNull(message = "El precio base es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio base debe ser mayor a 0")
    private BigDecimal precioBase;

    @NotNull(message = "La cantidad de camas es obligatoria")
    @Min(value = 1, message = "Debe haber al menos 1 cama")
    private Integer cantidadCamas;
}