package com.example.przedszkole.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final String secret;
    // Czas waznosci tokena: po godzinie uzytkownik musi zalogowac sie ponownie.
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 godzina

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    private SecretKey getSigningKey() {
        // Biblioteka JWT zamienia tekstowy sekret na klucz HMAC uzywany do podpisu i weryfikacji tokena.
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String rola) {
        // Token zawiera minimum danych potrzebnych frontendowi i backendowi: email oraz role uzytkownika.
        return Jwts.builder()
                .subject(email)
                .claim("rola", rola)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        // Weryfikacja podpisu chroni przed podrobieniem tokena albo zmiana roli po stronie klienta.
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRola(String token) {
        return extractAllClaims(token).get("rola", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            // Token jest poprawny tylko wtedy, gdy podpis sie zgadza i data wygasniecia jest w przyszlosci.
            Date expiration = extractAllClaims(token).getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            // Kazdy blad parsowania, podpisu albo wygasniecia traktujemy jako brak autoryzacji.
            return false;
        }
    }
}
