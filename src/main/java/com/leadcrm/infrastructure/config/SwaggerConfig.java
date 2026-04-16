package com.leadcrm.infrastructure.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public GroupedOpenApi leadApi() {
        return GroupedOpenApi.builder()
                .group("leads")
                .pathsToMatch("/api/leads/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi taskApi() {
        return GroupedOpenApi.builder()
                .group("tasks")
                .pathsToMatch("/api/tasks/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/api/users/**", "/api/auth/**")
                .build();
    }
}