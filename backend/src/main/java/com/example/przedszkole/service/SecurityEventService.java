package com.example.przedszkole.service;

import com.example.przedszkole.model.SecurityEvent;
import com.example.przedszkole.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Serwis zapisuje i odczytuje zdarzenia bezpieczenstwa.
@Service
public class SecurityEventService {

    // Stale okreslaja typy zdarzen zapisywanych w tabeli security_event.
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    public static final String LOGIN_FAILED = "LOGIN_FAILED";
    public static final String LOGIN_BLOCKED = "LOGIN_BLOCKED";

    // Repozytorium zapisuje i pobiera zdarzenia z bazy danych.
    private final SecurityEventRepository securityEventRepository;

    public SecurityEventService(SecurityEventRepository securityEventRepository) {
        this.securityEventRepository = securityEventRepository;
    }

    // Zapisuje pojedyncze zdarzenie bezpieczenstwa, np. bledne logowanie.
    public void record(String type, String email, String ipAddress, String details) {
        securityEventRepository.save(new SecurityEvent(type, normalize(email), ipAddress, details));
    }

    // Pobiera najnowsze zdarzenia do panelu administracyjnego.
    public List<SecurityEvent> latestEvents() {
        return securityEventRepository.findTop100ByOrderByCreatedAtDesc();
    }

    // Ujednolica email przed zapisem: usuwa spacje i zmienia litery na male.
    private String normalize(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
