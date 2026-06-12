package com.example.przedszkole.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

// Encja JPA reprezentujaca uwage przypisana do konkretnego dziecka.
@Entity
@Table(name = "uwagi")
public class Uwaga {

    // Klucz glowny uwagi.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tresc uwagi, np. informacja od nauczyciela.
    private String tresc;

    // Czas dodania uwagi.
    private LocalDateTime dataDodania;

    // Wiele uwag moze byc przypisanych do jednego dziecka.
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_dziecka")
    private Dziecko dziecko;

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public LocalDateTime getDataDodania() {
        return dataDodania;
    }

    public void setDataDodania(LocalDateTime dataDodania) {
        this.dataDodania = dataDodania;
    }

    public Dziecko getDziecko() {
        return dziecko;
    }

    public void setDziecko(Dziecko dziecko) {
        this.dziecko = dziecko;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
