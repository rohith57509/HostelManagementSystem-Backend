package com.hostel.hostelmanagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expiration;

    public JwtService(@Value("${app.jwt.secret}") String s, @Value("${app.jwt.expiration}") long e) {
        key = Keys.hmacShaKeyFor(s.getBytes(StandardCharsets.UTF_8));
        expiration = e;
    }

    public String generateToken(String email, String role) {
        Date n = new Date();
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(n)
                .expiration(new Date(n.getTime() + expiration))
                .signWith(key)
                .compact();
    }

    public String email(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean valid(String token) {
        try {
            email(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}