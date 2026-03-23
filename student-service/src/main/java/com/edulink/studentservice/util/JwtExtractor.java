package com.edulink.studentservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtExtractor {
    @Value("${jwt.secret}")
    private String secret;

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.toLowerCase().startsWith("bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }
        String token = header.substring(header.indexOf(' ') + 1).trim();
        while (token.toLowerCase().startsWith("bearer ")) {
            token = token.substring(token.indexOf(' ') + 1).trim();
        }
        return token;
    }

    public Long extractUserId(HttpServletRequest request) {
        String token = extractToken(request);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseClaimsJws(token).getBody();
        Object userIdObj = claims.get("userId");
        if (userIdObj instanceof String) {
            try {
                return Long.parseLong((String) userIdObj);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return userIdObj != null ? ((Number) userIdObj).longValue() : null;
    }

    public String extractEmail(HttpServletRequest request) {
        String token = extractToken(request);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
