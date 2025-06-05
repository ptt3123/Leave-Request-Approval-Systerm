package com.example.employee_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RCToAuthServiceConfig {

    @Bean(name = "authRestClient")
    public RestClient authRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://auth-service:8080/api/a/")
                .build();
    }

}
