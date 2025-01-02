package com.cortez.testapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long id; 
    private String nombres; 
    private String apellidos; 
    private String tipoDoc; 
    private String numeroDoc;
}
