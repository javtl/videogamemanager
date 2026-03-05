package com.videogamemanager.videogamemanager.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Video Game Manager API")
                        .version("1.0")
                        .description("API para la gestión de catálogo de videojuegos")
                        .contact(new Contact()
                                .name("FranG/Javtl")
                                .email("soporte@gamevault.com")));
    }
}
