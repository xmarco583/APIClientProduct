package com.cortez.testapi.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;

@OpenAPIDefinition(
    info = @Info(
        title = "Clientes y Productos API",
        description = "BFF que consume dos microservicios para obtener información de clientes y productos financieros",
        version = "1.0"
    ),
    servers={
        @Server(
            description = "Servidor de recursos",
            url="http://127.0.0.1:7000")
    },
    security = @SecurityRequirement(
        name = "Security token"
        )
    
)

@SecurityScheme(
    name = "Security token",
    type = SecuritySchemeType.HTTP,
    paramName = "Authorization",
    in = SecuritySchemeIn.HEADER,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {
    
}
