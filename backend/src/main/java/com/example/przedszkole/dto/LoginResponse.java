package com.example.przedszkole.dto;

// DTO zwracane po poprawnym logowaniu albo rejestracji.
public class LoginResponse {

    // Token JWT, ktory frontend wysyla pozniej w naglowku Authorization.
    private String token;

    // Email zalogowanego uzytkownika.
    private String email;

    // Rola uzytkownika, np. RODZIC, NAUCZYCIEL, DYREKCJA albo ADMIN.
    private String rola;

    public LoginResponse(String token) {
        this.token = token;
    }

    public LoginResponse(String token, String email, String rola) {
        this.token = token;
        this.email = email;
        this.rola = rola;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getRola() {
        return rola;
    }
}
