package com.example.przedszkole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

// Encja JPA dla powiadomienia przypisanego do dziecka.
@Entity
@Table(name = "Powiadomienie")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Powiadomienie {

    // Klucz glowny powiadomienia.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_powiadomienia", nullable = false)
    private Long id_powiadomienia;

    // Wiele powiadomien moze dotyczyc jednego dziecka.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dziecka", nullable = false)
    private Dziecko dziecko;


    public Powiadomienie() {}

    public Long getId_powiadomienia() { return id_powiadomienia; }
    public void setId_powiadomienia(Long id_powiadomienia) { this.id_powiadomienia = id_powiadomienia; }

    public Dziecko getDziecko() { return dziecko; }
    public void setDziecko(Dziecko dziecko) { this.dziecko = dziecko; }
}
