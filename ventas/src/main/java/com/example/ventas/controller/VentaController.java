package com.example.ventas.controller;

import com.example.ventas.dto.VentaDTO;
import com.example.ventas.model.Venta;
import com.example.ventas.service.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VentaController {

    @Autowired
    private VentasService ventasService;

    @GetMapping("/ventas/{id}")
    public VentaDTO GetVentaById(@PathVariable int id){
        return ventasService.GetVentaById(id);
    }

    @PostMapping("/ventas")
    public String CreateVenta(@RequestBody Venta venta){
        return ventasService.CreateVenta(venta);
    }

    @PutMapping("/ventas/{id}")
    public String UpdateVenta(@PathVariable int id, @RequestBody Venta venta){
        return ventasService.UpdateVenta(id, venta);
    }

    @DeleteMapping("/ventas/{id}")
    public String DeleteVenta(@PathVariable int id){
        return ventasService.DeleteVenta(id);
    }
}
