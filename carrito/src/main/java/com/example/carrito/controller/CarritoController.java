package com.example.carrito.controller;

import com.example.carrito.dto.CarritoDTO;
import com.example.carrito.dto.ProductoCantidadDTO;
import com.example.carrito.dto.ProductoDTO;
import com.example.carrito.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/carrito/{id}")
    public CarritoDTO GetCartsById(@PathVariable int id){
        return carritoService.GetCartsById(id);
    }

    @PostMapping("/carrito")
    public String CrearCarrito(@RequestBody List<ProductoCantidadDTO> listaProductos){
        return carritoService.CreateCart(listaProductos);
    }

    @PutMapping("/carrito/anadir-producto/{id}")
    public String anadirProductoAlCarrito(@PathVariable int id, @RequestBody ProductoCantidadDTO producto){
        return carritoService.AddProductToCart(id, producto);
    }

    @PutMapping("/carrito/quitar-producto/{id}")
    public String quitarProductoAlCarrito(@PathVariable int id, @RequestBody ProductoCantidadDTO producto){
        return carritoService.DeleteProductFromCart(id, producto);
    }

    @DeleteMapping("/carrito/{id}")
    public String deleteCarrito(@PathVariable int id){
        return carritoService.deleteCarrito(id);
    }
}
