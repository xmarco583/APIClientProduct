package com.cortez.usproducto.service;

import org.springframework.stereotype.Service;

import com.cortez.usproducto.data.Product;
import com.cortez.usproducto.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;

    public Flux<Product> getProductos(Long codigoCliente) {
        return productRepository.findByCodigoCliente(codigoCliente);
    }
}
