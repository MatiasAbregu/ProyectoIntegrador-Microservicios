package com.example.ventas.repository;

import com.example.ventas.dto.CarritoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "carrito")
public interface CarritoAPI {

    @GetMapping("carrito/{id}")
    CarritoDTO GetCartById(@PathVariable int id);
}
