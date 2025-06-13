package com.med.tci.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("""
                        http://localhost:4200""", """
                        http://217.65.146.13:4200""") // Remplacez par le domaine frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE");
                //.allowedHeaders("*")
                //.allowCredentials(true);
    }
}

