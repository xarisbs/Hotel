package com.upeu.hotel.tipohabitacion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tipoHabitacionOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hotel — Tipos de habitación API")
                        .description("API REST para gestión de tipos de habitación del hotel.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Hotel")
                                .email("hotel@upeu.edu.pe"))
                        .license(new License()
                                .name("Internal Use Only")
                                .url("https://upeu.edu.pe")));
    }
}
