package com.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

// Why this exists at all?
	// Spring Security automatically blocks requests unless configured.
// But:
	// We are not using Spring Security for authentication
	// We are using custom JWT filter
// So we must:
	// Disable Spring Security checks
	// Let Gateway filters handle security

@Configuration
public class SecurityConfig {
	
	// Reactive version of SecurityFilterChain
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		
		// CSRF is disabled
		// JWT is stateless
		// No cookies
		// CSRF only applies to session-based auth
		http.csrf(ServerHttpSecurity.CsrfSpec::disable)
				// Spring Security won’t block requests
				// JwtAuthenticationFilter will decide
				.authorizeExchange(exchange -> 
				exchange.anyExchange().permitAll());
		
		// Finalizes configuration
		return http.build();
	}
}
