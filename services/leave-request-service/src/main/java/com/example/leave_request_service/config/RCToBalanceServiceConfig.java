package com.example.leave_request_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RCToBalanceServiceConfig {

    @Bean(name = "balanceRestClient")
    public RestClient balanceRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://balance-service:8080/api/b")
                .build();
    }

}
