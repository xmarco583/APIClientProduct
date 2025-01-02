package com.cortez.usproducto.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.cortez.usproducto.data.Product;
import com.cortez.usproducto.repository.ProductRepository;
import com.cortez.usproducto.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductos_Success() {
        Long codigoCliente = 456L;
        Product product1 = new Product();
        product1.setCodigoCliente(codigoCliente);
        Product product2 = new Product();
        product2.setCodigoCliente(codigoCliente);

        // Simular el comportamiento del repositorio
        when(productRepository.findByCodigoCliente(anyLong())).thenReturn(Flux.just(product1, product2));

        // Ejecutar el método del servicio
        Flux<Product> result = productService.getProductos(codigoCliente);

        // Verificar el resultado usando StepVerifier
        StepVerifier.create(result)
            .expectNextMatches(returnedProduct -> {
                assertNotNull(returnedProduct);
                assertEquals(codigoCliente, returnedProduct.getCodigoCliente());
                return true;
            })
            .expectNextMatches(returnedProduct -> {
                assertNotNull(returnedProduct);
                assertEquals(codigoCliente, returnedProduct.getCodigoCliente());
                return true;
            })
            .verifyComplete();
    }

    @Test
    void getProductos_NotFound() {
        Long codigoCliente = 456L;

        // Simular el comportamiento del repositorio
        when(productRepository.findByCodigoCliente(anyLong())).thenReturn(Flux.empty());

        // Ejecutar el método del servicio
        Flux<Product> result = productService.getProductos(codigoCliente);

        // Verificar el resultado usando StepVerifier
        StepVerifier.create(result)
            .expectNextCount(0)
            .verifyComplete();
    }
}
