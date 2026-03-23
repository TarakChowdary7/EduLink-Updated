package com.edulink.studentservice.security;

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
import java.security.Key;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header == null) {
            logger.debug("JWT filter: Authorization header missing");
            System.out.println("JWT filter: Authorization header missing");
        } else if (!header.toLowerCase().startsWith("bearer ")) {
            logger.debug("JWT filter: Authorization header does not start with Bearer: " + header);
            System.out.println("JWT filter: Authorization header does not start with Bearer: " + header);
        }
        if (header != null && header.toLowerCase().startsWith("bearer ")) {
            String token = header.substring(header.indexOf(' ') + 1).trim();
            while (token.toLowerCase().startsWith("bearer ")) {
                token = token.substring(token.indexOf(' ') + 1).trim();
            }
            try {
                Key key = Keys.hmacShaKeyFor(secret.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                        .parseClaimsJws(token).getBody();
                logger.debug("JWT filter: token parsed successfully, subject=" + claims.getSubject() + ", role=" + claims.get("role", String.class));
                System.out.println("JWT filter: token parsed successfully, subject=" + claims.getSubject() + ", role=" + claims.get("role", String.class));
                String email = claims.getSubject();
                String role = claims.get("role", String.class);
                if (email != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            email, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.debug("JWT filter: authentication set for " + email + " with role " + role);
                    System.out.println("JWT filter: authentication set for " + email + " with role " + role);
                }
            } catch (ExpiredJwtException e) {
                logger.warn("JWT filter: Token expired: " + e.getMessage());
                System.out.println("JWT filter: Token expired: " + e.getMessage());
            } catch (UnsupportedJwtException e) {
                logger.warn("JWT filter: Unsupported JWT: " + e.getMessage());
                System.out.println("JWT filter: Unsupported JWT: " + e.getMessage());
            } catch (MalformedJwtException e) {
                logger.warn("JWT filter: Malformed JWT: " + e.getMessage());
                System.out.println("JWT filter: Malformed JWT: " + e.getMessage());
            } catch (SignatureException e) {
                logger.warn("JWT filter: Invalid JWT signature: " + e.getMessage());
                System.out.println("JWT filter: Invalid JWT signature: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.warn("JWT filter: JWT claims string is empty: " + e.getMessage());
                System.out.println("JWT filter: JWT claims string is empty: " + e.getMessage());
            } catch (Exception e) {
                logger.warn("JWT filter exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                System.out.println("JWT filter exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }
        chain.doFilter(req, res);
    }
}
