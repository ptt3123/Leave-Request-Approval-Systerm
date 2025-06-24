package com.example.leave_request_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RCToEmployeeServiceConfig {

    @Bean(name = "employeeRestClient")
    public RestClient employeeRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://employee-service:8080/api/e")
                .build();
    }

}
