package com.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.gateway.util.JwtUtil;

import reactor.core.publisher.Mono;

// Purpose: Intercept every request, check JWT, and decide whether it can proceed.
// GlobalFilter → runs for every route
// Why GlobalFilter?
	// API Gateway is reactive
	// Filters run before routing
	// This is where security belongs
@Component
public class JwtAuthenticationFilter implements GlobalFilter{
	private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    	// Extracts request path (example: /auth/login)
    	String path = exchange.getRequest().getURI().getPath();

        // PUBLIC ENDPOINTS (NO JWT REQUIRED)
    	// Don’t have JWT yet
    	// Skip JWT validation
    	// Forward request immediately
        if (path.equals("/auth/login") || path.equals("/auth/register")) {
            return chain.filter(exchange);
        }

        // Reads Bearer Authentication:
        	// Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
        String authHeader =
            exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Enforces Header must exists and must be Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange); //401 unauthorized
        }

        // Removes "Bearer" and extracts raw JWT
        String token = authHeader.substring(7);

        try {
        	// Verify Token, Decodes payload, Throws exception if invalid
            var claims = jwtUtil.extractClaims(token);

            // Forward user info to downstream services
            //Instead of every microservice parsing JWT
            	// Gateway validates once
            	// Gateway forwards trusted headers
            // Downstream services receive:
            	// X-Username: rizin
            	// X-Role: CUSTOMER
//            exchange.getRequest().mutate()
//                .header("X-Username", claims.getSubject())
//                .header("X-Role", claims.get("role").toString())
//                .build();
            ServerHttpRequest mutatedRequest = exchange.getRequest()
            	    .mutate()
            	    .header("X-Username", claims.getSubject())
            	    .header("X-Role", claims.get("role").toString())
            	    .build();

            	ServerWebExchange mutatedExchange = exchange
            	    .mutate()
            	    .request(mutatedRequest)
            	    .build();
            	return chain.filter(mutatedExchange);

        } catch (Exception e) {
            return unauthorized(exchange); // Token expired, signature invalid, Token malformed
        }

        // Request is allowed to pass

//        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
    	// Immediately stops request pipeline
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
