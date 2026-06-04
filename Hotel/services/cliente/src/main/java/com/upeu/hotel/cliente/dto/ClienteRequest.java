package com.upeu.hotel.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String documento;

    @Email
    private String email;

    private String telefono;
    private String nacionalidad;
}
