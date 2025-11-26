package com.example.ticketingcatalog.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ticketingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ticketing Catalog API")
                        .description("API REST para gestionar Eventos y Venues en memoria")
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio del proyecto")
                        .url("https://github.com/tu-repo"));
    }
}
