package com.cortez.usproducto.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.cortez.usproducto.controller.ProductController;
import com.cortez.usproducto.data.Product;
import com.cortez.usproducto.service.ProductService;

import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductos() {
        // Arrange
        Long codigoUnico = 124L;
        Product product1 = new Product();
        product1.setCodigoCliente(codigoUnico);
        product1.setNombre("Product 1");
        product1.setSaldo(10.0f);

        Product product2 = new Product();
        product2.setCodigoCliente(codigoUnico);
        product2.setNombre("Product 2");
        product2.setSaldo(20.0f);
        
        when(productService.getProductos(codigoUnico))
            .thenReturn(Flux.just(product1, product2));

        // Act & Assert
        webTestClient.get()
            .uri("/product/{codigoUnico}", codigoUnico)
            .header("X-Request-ID", "test-request-id")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Product.class)
            .hasSize(2)
            .consumeWith(response -> {
                List<Product> products = response.getResponseBody();
                assertNotNull(products);
                assertEquals(2, products.size());
                assertEquals("Cuenta de ahorros", products.get(0).getNombre());
                assertEquals("Credito visa", products.get(1).getNombre());
            });
    }

    @Test
    void getProductos_NotFound() {
        // Given
        Long codigoUnico = 999L;
        
        when(productService.getProductos(codigoUnico))
            .thenReturn(Flux.empty());

        // When & Then
        webTestClient.get()
            .uri("/product/{codigoUnico}", codigoUnico)
            .header("X-Request-ID", "test-request-id")
            .exchange()
            .expectStatus().isNotFound();
    }
}

