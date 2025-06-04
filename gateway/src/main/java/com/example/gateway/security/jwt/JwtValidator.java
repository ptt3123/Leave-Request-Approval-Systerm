package com.example.gateway.security.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import com.example.gateway.exception.InvalidJWTException;

@Component
public class JwtValidator {

    @Value("${jwt.public-key-path}")
    private String publicKeyPath;

    private PublicKey publicKey;

    @PostConstruct
    public void init() throws Exception {
        InputStream is = new ClassPathResource(publicKeyPath).getInputStream();
        String key = new String(is.readAllBytes())
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public Claims validateToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (claims.getExpiration() == null || claims.getExpiration().before(new Date())){
            throw new InvalidJWTException("Token expired");
        };

        if (claims.get("unm", String.class) == null ||
                claims.get("uid", Integer.class) == null ||
                claims.get("uim", Boolean.class) == null) {
            throw new InvalidJWTException("Missing required claims");
        }

        return claims;
    }
}
