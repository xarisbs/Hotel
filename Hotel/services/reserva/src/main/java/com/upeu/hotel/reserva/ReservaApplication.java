package com.upeu.hotel.reserva;

import com.upeu.hotel.reserva.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableConfigurationProperties(JwtProperties.class)
public class ReservaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReservaApplication.class, args);
	}
}