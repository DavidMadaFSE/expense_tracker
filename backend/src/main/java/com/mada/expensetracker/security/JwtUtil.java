package com.mada.expensetracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "thisisthesuperstrongkeyintheworld";
    
    // Create a JWT token for the given username (email)
    public String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (60000 * 30)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username (email) with a given token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Validate that the token is still valid (not expired, correct username)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (!extractAllClaims(token).getExpiration().after(new Date())) {
            // Token is still valid
            
        }
        
        return false;
    }

    // Extract information inside of token
    public Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return (Claims) Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }
}
