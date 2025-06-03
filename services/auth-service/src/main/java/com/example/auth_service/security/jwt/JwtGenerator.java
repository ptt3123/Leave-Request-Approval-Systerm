package com.example.auth_service.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtGenerator {

    @Value("${jwt.private-key-path}")
    private String privateKeyPath;

    @Value("${jwt.exp}")
    private long expirationMs;

    private PrivateKey privateKey;

    @PostConstruct
    public void init() {

        try (InputStream is = new ClassPathResource(privateKeyPath).getInputStream()) {
            String key = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

            log.info("Private key successfully loaded.");

        } catch (Exception e) {

            log.error("Failed to load private key from: {}", privateKeyPath, e);
            throw new IllegalStateException("Failed to load private key from " + privateKeyPath, e);
        }
    }

    public String generateToken(Integer uid, String unm, Boolean uim) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .claim("uid", uid)
                .claim("unm", unm)
                .claim("uim", uim)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}

