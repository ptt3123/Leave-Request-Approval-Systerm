package com.example.leave_request_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RCToRequestServiceConfig {

    @Bean(name = "requestRestClient")
    public RestClient requestRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://request-service:8080/api/r")
                .build();
    }

}
