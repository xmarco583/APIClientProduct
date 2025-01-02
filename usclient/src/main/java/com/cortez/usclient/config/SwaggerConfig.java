package com.cortez.usclient.config;

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
                .title("Microservicio de datos")
                .version("1.0")
                .description("Microservicio que devuelve datos personales de un cliente"))
            .addServersItem(new Server()
                                .url("http://127.0.0.1:3000")
                                .description("Servidor de datos"));
    }
}