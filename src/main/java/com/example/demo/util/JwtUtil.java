package com.example.demo.util;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

/**
 * JwtUtil is a helper class for:
 * 1) creating JWT tokens
 * 2) reading data (username) back from a JWT token
 * <p>
 * JWT (JSON Web Token) is commonly used in authentication systems.
 * After login, server gives a token to client, and client sends it back
 * on future requests (usually in Authorization header).
 */
@Component
public class JwtUtil {
    // NOTE: In a real application this should come from environment variables
    // or secure configuration, not hardcoded in source code.
    private static final String SECRET = "qaehstlGw6MHXMUokFIRHoULiKi5wRP2jsT8K5uLs7Z";
    // Convert the plain text SECRET into a SecretKey object that crypto APIs can use.
    // JWT signing with HS256 uses HMAC (a hash-based message authentication algorithm),
    // which requires raw bytes, not a Java String.
    // We use UTF-8 to convert characters to bytes in a consistent, cross-platform way.
    // This same key must be used for BOTH:
    // - signWith(...) when creating the token
    // - verifyWith(...) when validating/parsing the token
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * Creates a signed JWT token for a given username.
     * <p>
     * Token fields used:
     * - subject: who the token belongs to (here: username)
     * - issuedAt: when token was created
     * - expiration: when token becomes invalid (1 hour from now)
     * <p>
     * signWith(SECRET_KEY) cryptographically signs the token so it cannot
     * be modified by clients without detection.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Extracts the username (JWT subject) from the token.
     * <p>
     * Supports both formats:
     * - raw token: "eyJhbGciOi..."
     * - Authorization header value: "Bearer eyJhbGciOi..."
     * <p>
     * Steps:
     * 1) remove "Bearer " prefix if present
     * 2) verify signature using SECRET_KEY
     * 3) parse claims payload
     * 4) return "sub" (subject), which is the username
     */
    public String extractUsername(String token) {
        String jwt = token != null && token.startsWith("Bearer ") ? token.substring(7) : token;
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

}
