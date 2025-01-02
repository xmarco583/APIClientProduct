package com.cortez.testapi.service;


import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;


import com.cortez.testapi.config.security.Encryptor;

import com.cortez.testapi.dto.ClientDTO;
import com.cortez.testapi.dto.ClientProductDTO;
import com.cortez.testapi.dto.ProductDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientProductService {


    private final Encryptor encryptor;
    private final WebClient webClient;

    public Mono<ClientProductDTO> getCliente(String codigounico,String requestId) 
    {     
        log.info("Iniciando búsqueda: {}", codigounico);

        // Encriptar el código único una sola vez y devolver encriptado
        Long codigoDesencriptado = encryptor.desencriptarLong(codigounico);

        // Llamada al microservicio cliente
        Mono<ClientDTO> clienteMono = webClient.get()
                .uri("http://127.0.0.1:3000/client/" + codigoDesencriptado)
                .header("X-Request-ID", requestId)              
                .retrieve()
                .bodyToMono(ClientDTO.class)
                .doOnSuccess(cliente -> log.info("Lista de productos obtenida para cliente: {}, Request-ID: {}", codigounico, requestId));

        // Llamada al microservicio de productos financieros
        Flux<ProductDTO> productosFlux = webClient.get()
                .uri("http://127.0.0.1:4000/product/" + codigoDesencriptado)
                .header("X-Request-ID", requestId)
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                .doOnNext(products -> log.debug("Lista de productos obtenida para cliente: {}, Request-ID: {}", codigounico, requestId));

        // Combina los resultados y genera la respuesta
        return clienteMono.zipWith(productosFlux.collectList(), (cliente, productos) -> {
            ClientProductDTO clientProductDTO = new ClientProductDTO(cliente, productos);
            log.info("Respuesta generada: {}", codigounico);
            log.debug("Cantidad de productos encontrados: {}", productos.size());
            return clientProductDTO;
        }).doOnError(error -> log.error("Error al generar respuesta para {}: {}", codigounico, error.getMessage()));
    }

}
