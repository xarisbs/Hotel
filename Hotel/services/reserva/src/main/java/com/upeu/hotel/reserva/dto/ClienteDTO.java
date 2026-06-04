package com.upeu.hotel.reserva.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String documento;
    private String nombreCompleto;
}
