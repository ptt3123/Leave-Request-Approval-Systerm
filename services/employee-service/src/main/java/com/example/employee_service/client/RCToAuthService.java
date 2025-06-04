package com.example.employee_service.client;

import com.example.employee_service.dto.JWTData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RCToAuthService {

    private final RestClient authRestClient;

    public RCToAuthService(
            @Qualifier("authRestClient") RestClient authRestClient
    ) {
        this.authRestClient = authRestClient;
    }

    public ResponseEntity<String> generateJWT(JWTData data) {
        return authRestClient.post()
                .uri("/generate-jwt")
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toEntity(String.class);
    }
}