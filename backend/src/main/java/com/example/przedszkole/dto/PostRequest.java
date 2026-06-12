package com.example.przedszkole.dto;

// DTO z danymi potrzebnymi do utworzenia albo edycji komunikatu.
public class PostRequest {
    // Tytul komunikatu widoczny na frontendzie.
    public String tytul;

    // Tresc komunikatu.
    public String tresc;

    // Informacja, czy komunikat ma byc widoczny publicznie.
    public Boolean opublikowany;
}
