package com.edulink.examservice.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Value("${jwt.secret}") private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header != null && header.toLowerCase().startsWith("bearer ")) {
            String token = header.substring(header.indexOf(' ') + 1).trim();
            while (token.toLowerCase().startsWith("bearer ")) {
                token = token.substring(token.indexOf(' ') + 1).trim();
            }
            try {
                Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                        .build().parseClaimsJws(token).getBody();
                String email = claims.getSubject();
                String role = claims.get("role", String.class);
                String userId = claims.get("userId", String.class);

                if (role != null && role.startsWith("ROLE_")) {
                    role = role.substring("ROLE_".length());
                }

                if (email != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            email, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
                    auth.setDetails(userId != null ? userId : email);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                logger.warn("JWT parse failed in exam-service: " + e.getMessage());
            }
        }
        chain.doFilter(req, res);
    }
}
