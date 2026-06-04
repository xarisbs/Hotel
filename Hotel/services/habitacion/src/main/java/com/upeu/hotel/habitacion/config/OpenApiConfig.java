package com.upeu.hotel.habitacion.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {

    @Bean
    public OpenAPI habitacionOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hotel — Habitaciones API")
                        .description("API REST para gestión de habitaciones del hotel.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Hotel")
                                .email("hotel@upeu.edu.pe"))
                        .license(new License()
                                .name("Internal Use Only")
                                .url("https://upeu.edu.pe")));
    }
}
