package com.cortez.usproducto.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cortez.usproducto.data.Product;


import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    
    Flux<Product>findByCodigoCliente(Long codigoCliente);
}
