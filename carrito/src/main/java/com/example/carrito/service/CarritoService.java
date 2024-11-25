package com.example.carrito.service;

import com.example.carrito.dto.CarritoDTO;
import com.example.carrito.dto.ProductoCantidadDTO;
import com.example.carrito.dto.ProductoDTO;
import com.example.carrito.model.Carrito;
import com.example.carrito.repository.CarritoRepository;
import com.example.carrito.repository.ProductoAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService implements ICarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoAPI productoAPI;

    @Override
    @CircuitBreaker(name = "productos", fallbackMethod = "fallbackGetCartsById")
    @Retry(name = "productos")
    public CarritoDTO GetCartsById(int id) {
        Carrito carrito = carritoRepository.findById(id).orElse(null);
        if(carrito != null){
            CarritoDTO carritoDTO = new CarritoDTO();
            List<ProductoDTO> listaDeProductos = new ArrayList<>();
            carritoDTO.setId(carrito.getId());
            double montoTotal = 0.0;

            // Cargarle los productos
            for(ProductoCantidadDTO productoConCantidad : carrito.getProductos()){
                ProductoDTO producto = productoAPI.getProductoById(productoConCantidad.getCodigo());
                producto.setCantidad(productoConCantidad.getCantidad());
                montoTotal += producto.getCantidad() * producto.getPrecioUnitario();
                listaDeProductos.add(producto);
            }

            carritoDTO.setProductos(listaDeProductos);

            // Realizarle el calculo total
            carritoDTO.setPrecioFinal(montoTotal);

            return carritoDTO;
        } else return null;
    }

    @Override
    @CircuitBreaker(name = "productos", fallbackMethod = "fallbackProductoNoExiste")
    @Retry(name = "productos")
    public String CreateCart(List<ProductoCantidadDTO> listaProductos) {
        Carrito carrito = new Carrito();
        List<ProductoCantidadDTO> productosValidos = new ArrayList<>();

        for (ProductoCantidadDTO productoTemp : listaProductos) {
            ProductoCantidadDTO producto = productoAPI.getProductoCantidadById(productoTemp.getCodigo());

            if (producto != null) {
                boolean productoExiste = false;

                for (ProductoCantidadDTO productoValidoVerificacion : productosValidos) {
                    if (productoValidoVerificacion.getCodigo() == productoTemp.getCodigo()) {
                        productoValidoVerificacion.setCantidad(productoValidoVerificacion.getCantidad() + productoTemp.getCantidad());
                        productoExiste = true;
                        break;
                    }
                }

                if (!productoExiste) {
                    producto.setCantidad(productoTemp.getCantidad());
                    productosValidos.add(producto);
                }
            }
        }

        carrito.setProductos(productosValidos);
        carritoRepository.save(carrito);
        return "¡Carrito creado con éxito!";
    }

    @Override
    @CircuitBreaker(name = "productos", fallbackMethod = "fallbackProductoNoExiste")
    @Retry(name = "productos")
    public String AddProductToCart(int idCarrito, ProductoCantidadDTO producto) {
        Carrito carrito = carritoRepository.findById(idCarrito).orElse(null);

        if (carrito != null) {
            ProductoCantidadDTO productoExistente = productoAPI.getProductoCantidadById(producto.getCodigo());

            if (productoExistente != null) {
                boolean productoExiste = false;

                for (ProductoCantidadDTO productoValidoVerificacion : carrito.getProductos()) {
                    if (productoValidoVerificacion.getCodigo() == productoExistente.getCodigo()) {
                        productoValidoVerificacion.setCantidad(productoValidoVerificacion.getCantidad() + producto.getCantidad());
                        productoExiste = true;
                        break;
                    }
                }

                if (!productoExiste) {
                    carrito.getProductos().add(producto);
                }

                carritoRepository.save(carrito);
                return "¡Producto añadido con éxito!";
            }
            return "¡El producto que quisiste añadir no existe!";
        }
        return "¡El carrito que solicitaste añadir un producto no existe!";
    }

    @Override
    public String DeleteProductFromCart(int idCarrito, ProductoCantidadDTO producto) {
        Carrito carrito = carritoRepository.findById(idCarrito).orElse(null);

        if (carrito != null) {
            if (producto != null) {
                for (ProductoCantidadDTO productoValidoVerificacion : carrito.getProductos()) {
                    if (productoValidoVerificacion.getCodigo() == producto.getCodigo()) {

                        if (productoValidoVerificacion.getCantidad() - producto.getCantidad() < 0)
                            productoValidoVerificacion.setCantidad(0);
                        else
                            productoValidoVerificacion.setCantidad(productoValidoVerificacion.getCantidad() - producto.getCantidad());
                        break;
                    }
                }
                carritoRepository.save(carrito);
                return "¡Producto eliminado con éxito!";
            }
            return "¡El producto que quisiste eliminar no existe!";
        }
        return "¡El carrito que solicitaste quitar un producto no existe!";
    }

    @Override
    public String deleteCarrito(int id){
        carritoRepository.deleteById(id);
        return "¡Carrito eliminado con éxito!";
    }

    public String fallbackProductoNoExiste(int idCarrito, ProductoCantidadDTO producto, Throwable e){
        System.out.println("Error: " + e.getMessage());
        return "Error al intentar comunicarse con los productos, por favor reintente más tarde. Disculpe las molestias.";
    }

    public String fallbackProductoNoExiste(List<ProductoCantidadDTO> listaProductos, Throwable e){
        System.out.println("Error: " + e.getMessage());
        return "Error al intentar comunicarse con los productos, por favor reintente más tarde. Disculpe las molestias.";
    }

    public CarritoDTO fallbackGetCartsById(int id, Throwable e){
        System.out.println("Error: " + e.getMessage());
        return new CarritoDTO(0, null, 0.0);
    }
}