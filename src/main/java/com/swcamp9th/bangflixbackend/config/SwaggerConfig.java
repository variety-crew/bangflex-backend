package com.swcamp9th.bangflixbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Bang-flix API 명세서",
                 description = "Bang-flix API 명세서",
                 version = "v1"))

@Configuration
public class SwaggerConfig {

}