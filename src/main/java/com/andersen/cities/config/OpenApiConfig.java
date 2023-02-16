package com.andersen.cities.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import static com.andersen.cities.util.Constants.API_VERSION;
import static com.andersen.cities.util.Constants.BEARER_FORMAT;
import static com.andersen.cities.util.Constants.CITY_API;
import static com.andersen.cities.util.Constants.SHEME_BEARER;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme()
                        .in(SecurityScheme.In.HEADER)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(SHEME_BEARER)
                        .bearerFormat(BEARER_FORMAT))
                )
                .info(new Info().title(CITY_API).version(API_VERSION));
    }
}