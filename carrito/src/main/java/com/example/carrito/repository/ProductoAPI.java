package com.example.carrito.repository;

import com.example.carrito.dto.ProductoCantidadDTO;
import com.example.carrito.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productos")
public interface ProductoAPI {

    @GetMapping("productos/{id}")
    public ProductoCantidadDTO getProductoCantidadById(@PathVariable int id);

    @GetMapping("productos/{id}")
    public ProductoDTO getProductoById(@PathVariable int id);
}
