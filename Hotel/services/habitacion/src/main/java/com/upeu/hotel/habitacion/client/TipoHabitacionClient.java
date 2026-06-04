package com.upeu.hotel.habitacion.client;

import com.upeu.hotel.habitacion.dto.TipoHabitacionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tipo-habitacion")
public interface TipoHabitacionClient {

    @GetMapping("/api/v1/tipos-habitacion/{id}")
    TipoHabitacionDto findById(@PathVariable("id") Long id);
}
