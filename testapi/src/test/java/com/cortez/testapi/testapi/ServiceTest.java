package com.cortez.testapi.testapi;


import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.cortez.testapi.config.security.Encryptor;
import com.cortez.testapi.dto.ClientDTO;
import com.cortez.testapi.dto.ClientProductDTO;
import com.cortez.testapi.dto.ProductDTO;
import com.cortez.testapi.service.ClientProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock
    private WebClient webClient;
    
    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    
    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    
    @Mock
    private WebClient.ResponseSpec responseSpec;
    
    @Mock
    private Encryptor encryptor;
    
    @InjectMocks
    private ClientProductService clientProductService;

     @SuppressWarnings("unchecked")
    @Test
    public void testGetCliente() {
        String codigounico = "12345";
        String requestId = "request-id-123";
        Long codigoDesencriptado = 12345L;

        ClientDTO clientDTO = new ClientDTO();
        ProductDTO productDTO = new ProductDTO();
        List<ProductDTO> productList = Collections.singletonList(productDTO);

        // Mock encryptor
        when(encryptor.desencriptarLong(codigounico)).thenReturn(codigoDesencriptado);

        // Mock webClient for client call
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("http://127.0.0.1:3000/client/" + codigoDesencriptado)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header("X-Request-ID", requestId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ClientDTO.class)).thenReturn(Mono.just(clientDTO));

        // Mock webClient for product call
        when(requestHeadersUriSpec.uri("http://127.0.0.1:4000/product/" + codigoDesencriptado)).thenReturn(requestHeadersSpec);
        when(responseSpec.bodyToFlux(ProductDTO.class)).thenReturn(Flux.fromIterable(productList));

        // Call the service method
        Mono<ClientProductDTO> result = clientProductService.getCliente(codigounico, requestId);

        // Verify the result
        StepVerifier.create(result)
                .expectNextMatches(clientProductDTO -> 
                    clientProductDTO.getClient().equals(clientDTO) && 
                    clientProductDTO.getProducts().equals(productList))
                .verifyComplete();
    }

    
}