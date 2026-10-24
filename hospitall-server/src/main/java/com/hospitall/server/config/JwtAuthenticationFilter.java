package com.hospitall.server.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Key KEY;

    static {
        String secret = System.getenv("JWT_SECRET");
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT_SECRET environment variable must be set and at least 32 characters long");
        }
        KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username, null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        chain.doFilter(request, response);
    }
}
