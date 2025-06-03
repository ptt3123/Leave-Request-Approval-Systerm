package com.example.auth_service.controller;

import com.example.auth_service.dto.APIResponse;
import com.example.auth_service.dto.JWTData;
import com.example.auth_service.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/generate-jwt")
    public APIResponse<String> getJWT(
            @RequestBody JWTData jwtData
    ) {
        return APIResponse.success(authService.generateJWT(jwtData));
    }

}