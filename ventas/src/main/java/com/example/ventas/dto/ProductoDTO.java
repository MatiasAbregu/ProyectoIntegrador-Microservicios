package com.example.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class ProductoDTO {

    private int codigo;

    private String nombre;

    private String marca;

    private double precioUnitario;

    private int cantidad;
}
