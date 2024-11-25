package com.example.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CarritoDTO {

    private int id;

    private List<ProductoDTO> productos;

    private double precioFinal;

}