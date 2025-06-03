package com.example.auth_service.service.impl;

import com.example.auth_service.dto.JWTData;
import com.example.auth_service.security.jwt.JwtGenerator;
import com.example.auth_service.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final JwtGenerator jwtGenerator;

    public AuthServiceImpl(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public String generateJWT(JWTData data) {
        return jwtGenerator.generateToken(data.getId(), data.getName(), data.getIs_manager());
    }
}
