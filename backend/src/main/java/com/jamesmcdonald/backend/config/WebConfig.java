package com.jamesmcdonald.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global web configuration responsible for setting up CORS mappings.
 * Reads allowed origins from an environment variable.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Populated from the ALLOWED_ORIGINS environment variable.
     * Supports a comma-separated list of origins.
     */
    @Value("${ALLOWED_ORIGINS:*}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins.split("\\s*,\\s*"))
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}