package com.cortez.testapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class ProductDTO {
    private Long id; 
    private String tipoProducto; 
    private String nombre; 
    private float saldo; 
    
}
