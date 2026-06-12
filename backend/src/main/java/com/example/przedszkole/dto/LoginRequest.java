package com.example.przedszkole.dto;

// DTO z danymi wysylanymi przez frontend podczas logowania.
public class LoginRequest {

    // Email identyfikuje uzytkownika w systemie.
    private String email;

    // Haslo wpisane w formularzu logowania.
    private String haslo;

    public String getEmail() {
        return email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
}
