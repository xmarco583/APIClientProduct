package com.cortez.testapi.testapi;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; 

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import com.cortez.testapi.config.security.Encryptor;
import com.cortez.testapi.dto.ClientDTO;
import com.cortez.testapi.dto.ClientProductDTO;
import com.cortez.testapi.service.ClientProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ControllerTest {

   @Autowired
    protected MockMvc mockMvc;

    @Mock
    private ClientProductService clientProductService;

    @Mock
    private Encryptor encryptor;

    @Mock
    private JwtDecoder jwtDecoder;

    
    @Test
    void getCliente_ReactiveSuccess() throws Exception {
        String codigoUnico = "123";
        String requestId = "test-id";
        Long desencriptadoCodigoUnico = 123L;
        ClientDTO clientDTO = new ClientDTO();
        ClientProductDTO mockResponse = new ClientProductDTO(clientDTO, new ArrayList<>());

        // Mocking the Encryptor behavior
        when(encryptor.desencriptarLong(codigoUnico)).thenReturn(desencriptadoCodigoUnico);

        // Mocking the service call
        when(clientProductService.getCliente(eq(codigoUnico), anyString()))
            .thenReturn(Mono.just(mockResponse));

        mockMvc.perform(get("/api/clienteinfo/{codigoUnico}", codigoUnico)
                .with(jwt())  // Simular JWT
                .header("X-Request-ID", requestId))
            
            .andExpect(result -> {
                String responseBody = result.getResponse().getContentAsString();
                System.out.println("Response Body: " + responseBody); // Añadir log para verificar el contenido
                assertNotNull(responseBody); // Verificar que la respuesta no esté vacía
                if (responseBody != null && !responseBody.isEmpty()) {
                    ClientProductDTO response = new ObjectMapper().readValue(responseBody, ClientProductDTO.class);
                    assertNotNull(response);
                    assertEquals(clientDTO, response.getClient());
                    assertTrue(response.getProducts().isEmpty());
                }
            });
    }

}