package com.example.przedszkole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

// Encja JPA reprezentujaca zapis dziecka na zajecia dodatkowe.
@Entity
@Table(name = "Zapis_zajecia")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Zapis_zajecia {

    // Klucz glowny zapisu na zajecia.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zapisu_zajecia", nullable = false)
    private Long id_zapisu_zajecia;

    // Wiele zapisow moze dotyczyc jednego dziecka.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dziecka", nullable = false)
    private Dziecko dziecko;

    // Wiele zapisow moze dotyczyc tych samych zajec dodatkowych.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zajecie", nullable = false)
    private Zajecia_dodatkowe zajeciaDodatkowe;


    public Zapis_zajecia() {}

    public Long getId_zapisu_zajecia() { return id_zapisu_zajecia; }
    public void setId_zapisu_zajecia(Long id_zapisu_zajecia) { this.id_zapisu_zajecia = id_zapisu_zajecia; }

    public Dziecko getDziecko() { return dziecko; }
    public void setDziecko(Dziecko dziecko) { this.dziecko = dziecko; }

    public Zajecia_dodatkowe getZajeciaDodatkowe() {
        return zajeciaDodatkowe;
    }

    public void setZajeciaDodatkowe(Zajecia_dodatkowe zajeciaDodatkowe) {
        this.zajeciaDodatkowe = zajeciaDodatkowe;
    }

}
