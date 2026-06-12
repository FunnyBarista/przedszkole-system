package com.example.przedszkole.model;

import jakarta.persistence.*;
import java.time.LocalDate;

// Encja JPA opisujaca pojedynczy wpis jadlospisu.
@Entity
@Table(name = "Jadlospis")
public class Jadlospis {

    // Klucz glowny wpisu jadlospisu.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jadlospis_id", nullable = false)
    private Long jadlospis_id;

    // Data, dla ktorej obowiazuje dany jadlospis.
    @Column(name = "data", nullable = false)
    private LocalDate data;

    // Opis posilkow w danym dniu.
    @Column(name = "sniadanie", nullable = false)
    private String sniadanie;

    @Column(name = "obiad", nullable = false)
    private String obiad;

    public Jadlospis() {
    }

    public Long getJadlospis_id() {
        return jadlospis_id;
    }

    public void setJadlospis_id(Long jadlospis_id) {
        this.jadlospis_id = jadlospis_id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getSniadanie() {
        return sniadanie;
    }

    public void setSniadanie(String sniadanie) {
        this.sniadanie = sniadanie;
    }

    public String getObiad() {
        return obiad;
    }

    public void setObiad(String obiad) {
        this.obiad = obiad;
    }
}
