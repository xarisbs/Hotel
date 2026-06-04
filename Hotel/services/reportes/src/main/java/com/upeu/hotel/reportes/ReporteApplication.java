package com.upeu.hotel.reportes;

import com.upeu.hotel.reportes.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@EnableConfigurationProperties(JwtProperties.class)
public class ReporteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReporteApplication.class, args);
	}
}