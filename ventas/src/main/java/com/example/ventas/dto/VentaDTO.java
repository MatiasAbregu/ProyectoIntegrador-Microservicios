package com.example.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VentaDTO {

    private int id;

    private CarritoDTO carrito;

    private LocalDate fechaDeVenta;

}
