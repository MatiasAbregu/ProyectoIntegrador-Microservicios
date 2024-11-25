package com.example.ventas.service;

import com.example.ventas.dto.VentaDTO;
import com.example.ventas.model.Venta;
import com.example.ventas.repository.CarritoAPI;
import com.example.ventas.repository.VentaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VentasService implements IVentasService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private CarritoAPI carritoAPI;

    @Override
    @CircuitBreaker(name = "carrito", fallbackMethod = "fallbackGetVentaById")
    @Retry(name = "carrito")
    public VentaDTO GetVentaById(int id) {
        Venta venta = ventaRepository.findById(id).orElse(null);
        VentaDTO ventaDTO = new VentaDTO(venta.getId(), carritoAPI.GetCartById(venta.getCarritoId()), venta.getFechaDeVenta());
        return ventaDTO;
    }

    public VentaDTO fallbackGetVentaById(int id, Throwable e){
        return new VentaDTO(0, null, LocalDate.MAX);
    }

    @Override
    public String CreateVenta(Venta venta) {
        ventaRepository.save(venta);
        return "¡Venta creada con éxito!";
    }

    @Override
    public String UpdateVenta(int id, Venta venta) {
        Venta ventaEncontrada = ventaRepository.findById(id).orElse(null);
        ventaEncontrada.setCarritoId(venta.getCarritoId());
        ventaEncontrada.setFechaDeVenta(venta.getFechaDeVenta());
        ventaRepository.save(ventaEncontrada);
        return "¡Venta actualizada con éxito!";
    }

    @Override
    public String DeleteVenta(int id) {
        ventaRepository.deleteById(id);
        return "¡Venta eliminada con éxito!";
    }
}