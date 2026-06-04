package com.upeu.hotel.reserva.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProperties jwtProperties;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/reservas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/reservas/**").hasAnyRole("ADMIN", "RECEPCION")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/reservas/**").hasAnyRole("ADMIN", "RECEPCION")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/reservas/**").hasAnyRole("ADMIN", "RECEPCION")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/reservas/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecret());
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(new SecretKeySpec(keyBytes, "HmacSHA256")).build();
        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(jwtProperties.getIssuer()));
        return decoder;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("roles");
        converter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwt = new JwtAuthenticationConverter();
        jwt.setJwtGrantedAuthoritiesConverter(converter);
        return jwt;
    }
}
