package com.cortez.usproducto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservicio de productos")
                .version("1.0")
                .description("Microservicio que devuelve datos financieros de un cliente"))
            .addServersItem(new Server()
                                .url("http://127.0.0.1:4000")
                                .description("Servidor de datos"));
    }
}