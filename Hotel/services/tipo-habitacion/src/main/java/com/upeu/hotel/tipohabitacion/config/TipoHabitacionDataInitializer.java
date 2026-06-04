package com.upeu.hotel.tipohabitacion.config;

import com.upeu.hotel.tipohabitacion.entity.TipoHabitacion;
import com.upeu.hotel.tipohabitacion.repository.TipoHabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class TipoHabitacionDataInitializer {

    private final TipoHabitacionRepository repository;

    @Bean
    CommandLineRunner seedTiposHabitacion() {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            crear("Individual", "Habitación compacta ideal para viajeros solos", 1, 1, new BigDecimal("120.00"));
            crear("Doble", "Dos camas individuales con vista al jardín", 2, 2, new BigDecimal("180.00"));
            crear("Matrimonial", "Cama king size y amenities premium", 2, 1, new BigDecimal("220.00"));
            crear("Suite Junior", "Sala de estar separada y minibar", 3, 1, new BigDecimal("350.00"));
            crear("Suite Ejecutiva", "Vista panorámica, jacuzzi y servicio VIP", 4, 2, new BigDecimal("520.00"));
        };
    }

    private void crear(String nombre, String descripcion, int capacidad, int camas, BigDecimal precio) {
        repository.save(TipoHabitacion.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .capacidadMaxima(capacidad)
                .cantidadCamas(camas)
                .precioBase(precio)
                .build());
    }
}
