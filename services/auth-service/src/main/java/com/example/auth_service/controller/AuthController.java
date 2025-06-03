package com.example.auth_service.controller;

import com.example.auth_service.dto.JWTData;
import com.example.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/generate-jwt")
    public ResponseEntity<String> getJWT(
            @RequestBody JWTData jwtData
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.generateJWT(jwtData));
    }

}