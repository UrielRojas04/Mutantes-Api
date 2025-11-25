package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI mutantOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("ADN Analyzer Core API")
                .description("Servicio REST para clasificar secuencias de ADN (Mutante vs. Humano) y proveer métricas.")
                .version("1.0.1-BETA")
                .license(new License().name("Licencia de Uso Académico")));
    }
}