package com.cortez.testapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cortez.testapi.config.security.Encryptor;
import com.cortez.testapi.config.security.Tracker;
import com.cortez.testapi.dto.ClientProductDTO;
import com.cortez.testapi.service.ClientProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
@Slf4j
@Tag(name = "ClientProduct", description = "Controller para obtener información de cliente y productos")
public class ClientProductController {
   
   private final ClientProductService clientProductService;
   private final Encryptor encryptor;
   
   
   @PostMapping("/encriptar/{valor}")
   @ApiResponses(
    value = {@ApiResponse(
                responseCode = "200", 
                description = "Codigo encriptado", 
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = String.class))), 
            @ApiResponse(responseCode = "403", description = "NO autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @SecurityRequirement(name = "Bearer token")
    @Operation(summary = "Demo de encriptación", 
                description = "Encripta un valor de entrada y devuelve el valor encriptado para pruebas"
                )
    public String encriptar(
        @PathVariable String valor) {
        return encryptor.encriptar(valor);
    }


    @GetMapping("/clienteinfo/{codigounico}")
    @ApiResponses(
        value = {@ApiResponse(
                    responseCode = "200", 
                    description = "Cliente encontrado", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ClientProductDTO.class))), 
                @ApiResponse(responseCode = "403", description = "NO autorizado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @SecurityRequirement(name = "Bearer token")
    @Operation(summary = "Obtener cliente", 
               description = "Obtiene información de cliente y productos financieros"
               )
    public Mono<ClientProductDTO> getCliente(
        @Parameter(description = "Codigo único del cliente encriptado") @PathVariable String codigounico,
        @Parameter(description = "ID de la solicitud no requerido porque se genera aquí") @RequestHeader(value = "X-Request-ID",required = false) String requestId )
         {
            requestId = Tracker.generateTrackingId();
            return clientProductService.getCliente(codigounico,requestId);
    }


    
}
