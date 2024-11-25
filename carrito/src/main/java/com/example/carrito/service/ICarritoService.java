package com.example.carrito.service;

import com.example.carrito.dto.CarritoDTO;
import com.example.carrito.dto.ProductoCantidadDTO;
import com.example.carrito.model.Carrito;

import java.util.List;

public interface ICarritoService {

    CarritoDTO GetCartsById(int id);

    String CreateCart(List<ProductoCantidadDTO> listaProductos);

    String AddProductToCart(int idCarrito, ProductoCantidadDTO producto);

    String DeleteProductFromCart(int idCarrito, ProductoCantidadDTO producto);

    String deleteCarrito(int id);
}