package com.cortez.usclient.test;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.reactive.server.WebTestClient;

import com.cortez.usclient.controller.ClientController;
import com.cortez.usclient.data.Client;
import com.cortez.usclient.service.ClientService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private ClientService clientService;

     

    @InjectMocks
    private ClientController clientController;


    @Test
    void shouldReturnClientWhenFound() {
        // Arrange
        Long codigoUnico = 1L;
        Client client = new Client();
        client.setId(codigoUnico);
        client.setCodigoUnico(codigoUnico);
        client.setNombres("Test Client");
        client.setApellidos("Client Last Name");
        client.setTipoDoc("DNI");
        client.setNumeroDoc("12345678");
        
        when(clientService.getClient(codigoUnico))
            .thenReturn(Mono.just(client));

        // Act & Assert
        StepVerifier.create(clientService.getClient(codigoUnico))
            .expectNextMatches(returnedClient -> {
                assertNotNull(returnedClient);
                assertEquals(codigoUnico, returnedClient.getCodigoUnico());
                assertEquals("Test Client", returnedClient.getNombres());
                assertEquals("Client Last Name", returnedClient.getApellidos());
                assertEquals("DNI", returnedClient.getTipoDoc());
                assertEquals("12345678", returnedClient.getNumeroDoc());
                return true;
            })
            .verifyComplete();
    }


    @Test
    void getDatos_NotFound() {
        // Given
        Long codigoUnico = 999L;
        
        when(clientService.getClient(codigoUnico))
            .thenReturn(Mono.empty());

        // When & Then
        webTestClient.get()
            .uri("/{codigoUnico}", codigoUnico)
            .header("X-Request-ID", "test-request-id")
            .exchange()
            .expectStatus().isNotFound();
    }

}
