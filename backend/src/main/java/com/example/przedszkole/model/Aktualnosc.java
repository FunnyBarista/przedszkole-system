package com.example.przedszkole.model;

import jakarta.persistence.*;
import java.time.LocalDate;

// Encja JPA dla starszego typu aktualnosci/komunikatu.
@Entity
@Table(name = "Aktualnosc")
public class Aktualnosc {

    // Klucz glowny aktualnosci.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aktualnosc_id", nullable = false)
    private Long aktualnosc_id;

    // Tytul, tresc, data dodania i typ aktualnosci.
    @Column(name = "tytul", nullable = false)
    private String tytul;

    @Column(name = "tresc", nullable = false)
    private String tresc;

    @Column(name = "data_dodania", nullable = false)
    private LocalDate data_dodania;

    @Column(name = "typ", nullable = false)
    private String typ;

    public Aktualnosc() {
    }

    public Long getAktualnosc_id() {
        return aktualnosc_id;
    }

    public void setAktualnosc_id(Long aktualnosc_id) {
        this.aktualnosc_id = aktualnosc_id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public LocalDate getData_dodania() {
        return data_dodania;
    }

    public void setData_dodania(LocalDate data_dodania) {
        this.data_dodania = data_dodania;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
