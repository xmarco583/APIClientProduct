package com.cortez.usclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cortez.usclient.data.Client;
import com.cortez.usclient.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Slf4j
@Tag(name = "ClientController", description = "Controller para obtener información de cliente")
public class ClientController {
    private final ClientService clientService;

    @ApiResponses(
        value = {@ApiResponse(
                    responseCode = "200", 
                    description = "Cliente encontrado", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Client.class))), 
                @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @Operation(summary = "Obtener cliente", 
               description = "Obtiene información personal del cliente"
               )
    @GetMapping("/{codigoUnico}")
    public Mono<Client> getDatos(
        @Parameter(description = "Codigo único del cliente") @PathVariable Long codigoUnico,
        @Parameter(description = "ID de la solicitud")@RequestHeader("X-Request-ID") String requestId) {
        log.info("Iniciando búsqueda de cliente con código: {}", codigoUnico);
        return clientService.getClient(codigoUnico)
            .doOnSuccess(client -> log.info("Búsqueda de cliente completada para cliente: {}, Tracking ID: {}", codigoUnico, requestId))
            .doOnError(error -> log.error("Error al buscar cliente con código {}: {}", codigoUnico, error.getMessage()))
            .doFinally(signalType -> log.debug("Finalizada búsqueda de cliente. Signal: {}", signalType));
    }
}
