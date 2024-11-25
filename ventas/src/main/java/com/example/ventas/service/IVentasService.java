package com.example.ventas.service;

import com.example.ventas.dto.VentaDTO;
import com.example.ventas.model.Venta;

public interface IVentasService {

    VentaDTO GetVentaById(int id);

    String CreateVenta(Venta venta);

    String UpdateVenta(int id, Venta venta);

    String DeleteVenta(int id);
}