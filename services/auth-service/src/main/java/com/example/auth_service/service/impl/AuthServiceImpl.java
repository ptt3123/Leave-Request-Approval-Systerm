package com.example.auth_service.service.impl;

import com.example.auth_service.dto.JWTData;
import com.example.auth_service.security.jwt.JwtGenerator;
import com.example.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Override
    public String generateJWT(JWTData data) {
        return jwtGenerator.generateToken(data.getId(), data.getName(), data.getIs_manager());
    }
}
