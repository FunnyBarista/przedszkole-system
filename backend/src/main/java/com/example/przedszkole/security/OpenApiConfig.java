package com.example.przedszkole.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        // Konfiguracja OpenAPI/Swaggera informuje dokumentacje, ze endpointy uzywaja tokena JWT.
        return new OpenAPI()
                // "bearerAuth" dodaje w Swagger UI przycisk Authorize, gdzie mozna wkleic token z logowania.
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        // Definicja schematu: token jest przesylany jako naglowek Authorization: Bearer <JWT>.
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        // HTTP bearer to standardowy sposob przekazywania tokenow w REST API.
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        // bearerFormat jest opisem dla dokumentacji; tutaj podpowiada, ze token ma format JWT.
                                        .bearerFormat("JWT")
                        )
                );
    }
}
