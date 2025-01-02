package com.cortez.usclient.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cortez.usclient.data.Client;
import com.cortez.usclient.repository.ClientRepository;
import com.cortez.usclient.service.ClientService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getClient_Success() {
        Long codigoUnico = 123L;
        Client client = new Client();
        client.setCodigoUnico(codigoUnico);

        // Simular el comportamiento del repositorio
        when(clientRepository.findByCodigoUnico(anyLong())).thenReturn(Mono.just(client));

        // Ejecutar el método del servicio
        Mono<Client> result = clientService.getClient(codigoUnico);

        // Verificar el resultado usando StepVerifier
        StepVerifier.create(result)
            .expectNextMatches(returnedClient -> {
                assertNotNull(returnedClient);
                assertEquals(codigoUnico, returnedClient.getCodigoUnico());
                return true;
            })
            .verifyComplete();
    }

    @Test
    void getClient_NotFound() {
        Long codigoUnico = 123L;

        // Simular el comportamiento del repositorio
        when(clientRepository.findByCodigoUnico(anyLong())).thenReturn(Mono.empty());

        // Ejecutar el método del servicio
        Mono<Client> result = clientService.getClient(codigoUnico);

        // Verificar el resultado usando StepVerifier
        StepVerifier.create(result)
            .expectNextCount(0)
            .verifyComplete();
    }
}
