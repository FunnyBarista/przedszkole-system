package com.example.przedszkole.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Encja JPA opisujaca zajecia dodatkowe dostepne w przedszkolu.
@Entity
@Table(name = "Zajecia_dodatkowe")
public class Zajecia_dodatkowe {

    // Klucz glowny zajec dodatkowych.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zajecie", nullable = false)
    private Long id_zajecie;

    // Podstawowe informacje o zajeciach.
    @Column(name = "nazwa", nullable = false)
    private String nazwa;

    @Column(name = "opis", nullable = false)
    private String opis;

    @Column(name = "prowadzacy", nullable = false)
    private String prowadzacy;

    @Column(name = "dzien_tygodnia", nullable = false)
    private String dzien_tygodnia;

    @Column(name = "godzina", nullable = false)
    private LocalTime godzina;

    // Jedne zajecia moga miec wiele zapisow dzieci.
    @OneToMany(mappedBy = "zajeciaDodatkowe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zapis_zajecia> zapisy_zajecia = new ArrayList<>();

    public Zajecia_dodatkowe() {
    }

    public Long getId_zajecie() {
        return id_zajecie;
    }

    public void setId_zajecie(Long id_zajecie) {
        this.id_zajecie = id_zajecie;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getProwadzacy() {
        return prowadzacy;
    }

    public void setProwadzacy(String prowadzacy) {
        this.prowadzacy = prowadzacy;
    }

    public String getDzien_tygodnia() {
        return dzien_tygodnia;
    }

    public void setDzien_tygodnia(String dzien_tygodnia) {
        this.dzien_tygodnia = dzien_tygodnia;
    }

    public LocalTime getGodzina() {
        return godzina;
    }

    public void setGodzina(LocalTime godzina) {
        this.godzina = godzina;
    }

    public List<Zapis_zajecia> getZapisy_zajecia() {
        return zapisy_zajecia;
    }

    public void setZapisy_zajecia(List<Zapis_zajecia> zapisy_zajecia) {
        this.zapisy_zajecia = zapisy_zajecia;
    }
}
