package com.cortez.usproducto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cortez.usproducto.data.Product;
import com.cortez.usproducto.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
@Tag(name = "ProductController", description = "Controller para obtener información de productos")
public class ProductController {

    private final ProductService productService;

    @ApiResponses(
        value = {@ApiResponse(
                    responseCode = "200", 
                    description = "Productos encontrados", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Product.class))), 
                @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @Operation(summary = "Obtener productos", 
               description = "Obtiene información de productos del cliente"
               )
    @GetMapping("/{codigoUnico}")
    public Flux<Product> getProducts(
         @Parameter(description = "Codigo único del cliente") @PathVariable Long codigoUnico,
         @Parameter(description = "ID de la solicitud") @RequestHeader("X-Request-ID") String requestId) {
        
        log.info("Iniciando búsqueda de productos para cliente: {}", codigoUnico);
        return productService.getProductos(codigoUnico)
            .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Productos no encontrados")))
            .doOnComplete(() -> log.info("Request-ID: {}", requestId))
            .doOnError(error -> log.error("Error al buscar productos del cliente {}: {}", codigoUnico, error.getMessage()));
            
    }
}
