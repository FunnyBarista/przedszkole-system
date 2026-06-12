package com.example.przedszkole.dto;

// DTO z danymi wysylanymi przez formularz rejestracji rodzica.
public class RegisterRequest {

    // Dane osobowe nowego rodzica.
    private String imie;
    private String nazwisko;

    // Email bedzie loginem uzytkownika.
    private String email;

    // Haslo z formularza. Przed zapisem do bazy jest hashowane przez BCrypt.
    private String haslo;

    // Numer telefonu rodzica.
    private String telefon;

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
