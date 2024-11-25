package com.example.productos.service;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoByCodigo(int codigo) {
        return productoRepository.findById(codigo).orElse(null);
    }

    @Override
    public void createProducto(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void updateProducto(int codigo, Producto producto) {
        Producto productoActualizar = productoRepository.findById(codigo).orElse(null);
        productoActualizar.setNombre(producto.getNombre());
        productoActualizar.setMarca(producto.getMarca());
        productoActualizar.setPrecioUnitario(producto.getPrecioUnitario());
        productoRepository.save(productoActualizar);
    }

    @Override
    public void deleteProducto(int codigo) {
        productoRepository.deleteById(codigo);
    }
}