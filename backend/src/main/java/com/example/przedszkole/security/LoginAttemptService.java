package com.example.przedszkole.security;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    // Po tylu blednych probach konto/email zostaje tymczasowo zablokowane.
    private static final int MAX_ATTEMPTS = 5;
    // Dlugosc blokady po przekroczeniu limitu prob logowania.
    private static final Duration LOCK_TIME = Duration.ofMinutes(15);

    // Proby trzymamy w pamieci aplikacji. ConcurrentHashMap jest bezpieczna przy rownoleglych requestach.
    private final Map<String, AttemptInfo> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String email) {
        // Normalizacja sprawia, ze "TEST@MAIL.PL" i "test@mail.pl" licza sie jako ten sam login.
        AttemptInfo info = attempts.get(normalize(email));

        if (info == null || info.blockedUntil == null) {
            return false;
        }

        if (Instant.now().isAfter(info.blockedUntil)) {
            // Po uplywie blokady czyscimy licznik, zeby uzytkownik mogl sprobowac ponownie.
            attempts.remove(normalize(email));
            return false;
        }

        return true;
    }

    public void loginSucceeded(String email) {
        // Poprawne logowanie kasuje poprzednie bledne proby dla tego adresu.
        attempts.remove(normalize(email));
    }

    public void loginFailed(String email) {
        attempts.compute(normalize(email), (key, current) -> {
            AttemptInfo info = current == null ? new AttemptInfo() : current;
            info.failedAttempts++;

            if (info.failedAttempts >= MAX_ATTEMPTS) {
                // Po przekroczeniu limitu ustawiamy czas, do ktorego logowanie ma byc blokowane.
                info.blockedUntil = Instant.now().plus(LOCK_TIME);
            }

            return info;
        });
    }

    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }

    public long getLockMinutes() {
        return LOCK_TIME.toMinutes();
    }

    private String normalize(String email) {
        // Pusty string dla null pozwala uniknac wyjatku i nadal policzyc probe jako nieudana.
        return email == null ? "" : email.trim().toLowerCase();
    }

    private static class AttemptInfo {
        // Licznik blednych hasel dla jednego emaila.
        private int failedAttempts;
        // Jesli nie jest null, oznacza moment konca blokady.
        private Instant blockedUntil;
    }
}
