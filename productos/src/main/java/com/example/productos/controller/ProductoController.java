package com.example.productos.controller;

import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public List<Producto> getAllProductos(){
        return productoService.getAllProductos();
    }

    @GetMapping("/productos/{codigo}")
    public Producto getProductoByCodigo(@PathVariable int codigo){
        return productoService.getProductoByCodigo(codigo);
    }

    @PostMapping("/productos")
    public String createProducto(@RequestBody Producto producto){
        productoService.createProducto(producto);
        return "¡Producto creado con éxito!";
    }

    @PutMapping("/productos/{codigo}")
    public String updateProducto(@PathVariable int codigo, @RequestBody Producto producto){
        productoService.updateProducto(codigo, producto);
        return "¡Producto actualizado con éxito!";
    }

    @DeleteMapping("/productos/{codigo}")
    public String deleteProducto(@PathVariable int codigo){
        productoService.deleteProducto(codigo);
        return "¡Producto eliminado con éxito!";
    }
}
