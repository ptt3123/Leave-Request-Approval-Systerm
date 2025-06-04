package com.example.gateway.filter;

import com.example.gateway.exception.InvalidJWTException;
import com.example.gateway.security.jwt.JwtValidator;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class JWTFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtValidator jwtValidator;

    private static final List<String> WHITELIST = List.of(
            "/api/login",
            "/api/register"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // Bỏ qua các public route
        if (WHITELIST.contains(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return this.onError(
                    exchange,
                    "Missing or invalid Authorization header",
                    HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = jwtValidator.validateToken(token);

            // Gắn user info vào header mới và build lại request
            ServerHttpRequest newRequest = exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", claims.get("uid").toString())
                    .header("X-User-Name", claims.get("unm").toString())
                    .header("X-User-Is-Manager", claims.get("uim").toString())
                    .build();

            // Build lại exchange với request mới
            ServerWebExchange newExchange = exchange.mutate()
                    .request(newRequest)
                    .build();

            return chain.filter(newExchange);

        } catch (InvalidJWTException e) {

            log.error("Invalid or expired token: ", e);
            return this.onError(exchange, "Invalid or expired token!", HttpStatus.UNAUTHORIZED);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        DataBufferFactory bufferFactory = response.bufferFactory();
        return response.writeWith(Mono.just(bufferFactory.wrap(err.getBytes())));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}