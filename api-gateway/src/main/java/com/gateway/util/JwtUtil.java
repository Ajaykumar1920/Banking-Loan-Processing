package com.gateway.util;

import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// Purpose: Given a JWT token string, verify it and extract user data from it.

public class JwtUtil {

	// Same secret key used when the JWT was created (auth-service)
	// HMAC requires minimum 32 bytes
	private static final String SECRET_KEY = 
			"this-is-a-very-secure-secret-key-with-32-bytes-minimum";

	// Converts string → cryptographic key
	// Used to verify JWT signature
	private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	// Verifies JWT signature
	// Verifies token structure
	// Verifies token is not tampered
	// Parses payload
	// Returns Claims
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
