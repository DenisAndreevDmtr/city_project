package com.andersen.cities.config;

import static com.andersen.cities.util.Constants.BEARER_FORMAT;
import static com.andersen.cities.util.Constants.SHEME_BEARER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

@ExtendWith(MockitoExtension.class)
class OpenApiConfigTest {
    @InjectMocks
    private OpenApiConfig openApiConfig;

    @Test
    public void shouldReturnFilledOpenApi_whenCustomOpenApi() {
        OpenAPI actual = openApiConfig.customOpenApi();
        Components components = actual.getComponents();
        SecurityScheme expectedScheme = new SecurityScheme().in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme(SHEME_BEARER)
                .bearerFormat(BEARER_FORMAT);
        assertEquals(expectedScheme, components.getSecuritySchemes().get(HttpHeaders.AUTHORIZATION));
    }
}