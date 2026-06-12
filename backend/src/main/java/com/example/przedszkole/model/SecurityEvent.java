package com.example.przedszkole.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

// Encja JPA przechowujaca logi zdarzen bezpieczenstwa.
@Entity
@Table(name = "security_event")
public class SecurityEvent {

    // Klucz glowny wpisu w dzienniku zdarzen.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Typ zdarzenia, np. LOGIN_SUCCESS, LOGIN_FAILED albo LOGIN_BLOCKED.
    @Column(nullable = false, length = 50)
    private String type;

    // Email, ktorego dotyczylo zdarzenie.
    @Column(length = 100)
    private String email;

    // Adres IP klienta zapisany na potrzeby monitorowania.
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    // Czas wystapienia zdarzenia.
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Krotki opis zdarzenia widoczny w logach.
    @Column(length = 500)
    private String details;

    public SecurityEvent() {
    }

    // Konstruktor pomocniczy uzywany przy zapisywaniu nowego zdarzenia.
    public SecurityEvent(String type, String email, String ipAddress, String details) {
        this.type = type;
        this.email = email;
        this.ipAddress = ipAddress;
        this.details = details;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
