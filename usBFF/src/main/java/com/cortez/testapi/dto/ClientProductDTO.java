package com.cortez.testapi.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@NoArgsConstructor
public class ClientProductDTO {
    private ClientDTO client; 
    private List<ProductDTO> products;

    public ClientProductDTO(ClientDTO client, List<ProductDTO> products) {
         this.client = client; this.products = products; 
        }
}
