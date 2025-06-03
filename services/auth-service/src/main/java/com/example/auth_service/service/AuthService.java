package com.example.auth_service.service;

import com.example.auth_service.dto.JWTData;

public interface AuthService {
    public String generateJWT(JWTData data);
}
