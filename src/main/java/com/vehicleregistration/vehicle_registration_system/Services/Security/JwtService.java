package com.vehicleregistration.vehicle_registration_system.Services.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    // ── 1. Generate token ─────────────────────────────────
    // Takes the user's email and creates a signed token
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    // ── 2. Extract email from token ───────────────────────
    // Opens the token and reads the email stored inside
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // ── 3. Validate token ─────────────────────────────────
    // Checks email matches and token is not expired
    public boolean isTokenValid(String token, String email) {
        String extractedEmail = extractEmail(token);
        boolean notExpired = !getClaims(token)
                .getExpiration()
                .before(new Date());
        return extractedEmail.equals(email) && notExpired;
    }

    // ── Private helpers ───────────────────────────────────

    // Opens the token and returns all data inside it
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Converts secret key string to a Key object
    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}