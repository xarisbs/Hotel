package com.upeu.hotel.habitacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.upeu.hotel.habitacion.config.JwtProperties;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(JwtProperties.class)
public class HabitacionApplication {
	public static void main(String[] args) {
		SpringApplication.run(HabitacionApplication.class, args);
	}
}
