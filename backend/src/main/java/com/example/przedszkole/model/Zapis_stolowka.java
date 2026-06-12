package com.example.przedszkole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

// Encja JPA reprezentujaca zapis dziecka na stolowke.
@Entity
@Table(name = "Zapis_stolowka")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Zapis_stolowka {

    // Klucz glowny zapisu na stolowke.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zapisu_stolowka", nullable = false)
    private Long id_zapisu_stolowka;

    // Wiele zapisow stolowkowych moze dotyczyc jednego dziecka.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dziecka", nullable = false)
    private Dziecko dziecko;

    public Zapis_stolowka() {}

    public Long getId_zapisu_stolowka() { return id_zapisu_stolowka; }
    public void setId_zapisu_stolowka(Long id_zapisu_stolowka) { this.id_zapisu_stolowka = id_zapisu_stolowka; }

    public Dziecko getDziecko() { return dziecko; }
    public void setDziecko(Dziecko dziecko) { this.dziecko = dziecko; }
}
