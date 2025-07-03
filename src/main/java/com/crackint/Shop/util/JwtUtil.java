package com.crackint.Shop.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    // ✅ Token valid for 10 hours
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 10;

    // ✅ Replace this with a strong secret (move to env in prod)
    private final String secret = "my-secret-key-should-be-very-long-and-secure-123456";

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ✅ Generate JWT using username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Extract expiration
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ✅ Extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // ✅ Parse claims
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            System.out.println("❌ Invalid JWT: " + e.getMessage());
            throw e;
        }
    }

    // ✅ Check if expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ✅ Validate token using UserDetails
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        boolean expired = isTokenExpired(token);
        System.out.println("🔍 Token username: " + username);
        System.out.println("✅ DB username: " + userDetails.getUsername());
        System.out.println("✅ Is expired? " + expired);
        return username.equals(userDetails.getUsername()) && !expired;
    }

    // ✅ Optional: Validate using only username (e.g., in tests)
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }
}
