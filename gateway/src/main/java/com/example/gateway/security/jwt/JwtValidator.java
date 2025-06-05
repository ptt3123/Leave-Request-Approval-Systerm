package com.example.gateway.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import com.example.gateway.exception.InvalidJWTException;

@Component
@Slf4j
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

    private Claims parseToken(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (SignatureException ex) {
            // Token bị giả mạo - chữ ký sai
            log.error("Invalid JWT signature", ex);
            throw new InvalidJWTException("Invalid JWT signature");

        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token", ex);
            throw new InvalidJWTException("Expired JWT token");

        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token format", ex);
            throw new InvalidJWTException("Invalid JWT token format");

        } catch (Exception ex) {
            log.error("JWT token validation failed", ex);
            throw new InvalidJWTException("JWT token validation failed");
        }
    }

    public Claims validateToken(String token) {
        Claims claims = parseToken(token);

        if (claims.get("unm", String.class) == null ||
                claims.get("uid", Integer.class) == null ||
                claims.get("uim", Boolean.class) == null) {
            throw new InvalidJWTException("Missing required claims");
        }

        return claims;
    }
}
