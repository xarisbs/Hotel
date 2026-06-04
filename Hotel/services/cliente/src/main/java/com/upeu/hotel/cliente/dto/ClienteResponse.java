package com.upeu.hotel.cliente.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String documento;
    private String dni;
    private String email;
    private String correo;
    private String telefono;
    private String nacionalidad;
    private String nombreCompleto;
    private LocalDateTime fechaRegistro;
}
