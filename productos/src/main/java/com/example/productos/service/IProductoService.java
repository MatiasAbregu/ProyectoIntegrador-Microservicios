package com.example.productos.service;

import com.example.productos.model.Producto;

import java.util.List;

public interface IProductoService {

    List<Producto> getAllProductos();

    Producto getProductoByCodigo(int codigo);

    void createProducto(Producto producto);

    void updateProducto(int codigo, Producto producto);

    void deleteProducto(int codigo);
}