package com.example.carrito.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Embeddable
public class ProductoCantidadDTO {

    private int codigo;

    private int cantidad;
}
