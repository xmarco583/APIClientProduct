package com.cortez.testapi.config.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WcConfig {
    @Bean 
    public WebClient webClient(WebClient.Builder builder) { 
        return builder.build(); 
    }
}
