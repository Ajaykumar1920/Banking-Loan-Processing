package com.gateway.util;

import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	private static final String SECRET_KEY =
	        "this-is-a-very-secure-secret-key-with-32-bytes-minimum";

	    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	    public Claims extractClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }
}
