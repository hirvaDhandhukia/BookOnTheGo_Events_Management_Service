package com.bookonthego.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // Private secret key used for signing JWT tokens
    private final String secretKey = "yourSecretKey";

    // Getter method to access the secret key
    public String getSecretKey() {
        return secretKey;
    }

    // Method to extract user ID from the JWT token
    public String extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // Use the secretKey to parse the token
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Assuming the subject is the userId
    }

    // Method to generate a JWT token with userId as the subject
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId) // Set userId as the subject
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretKey) // Sign the token with secretKey
                .compact();
    }

    // Method to validate the JWT token
    public boolean validateToken(String token, @org.jetbrains.annotations.NotNull String userId) {
        String extractedUserId = extractUserId(token);
        return (userId.equals(extractedUserId));
    }
}
