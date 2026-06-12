package com.example.przedszkole.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

// Encja JPA reprezentujaca komunikat/aktualnosc publikowana w systemie.
@Entity
@Table(name = "Post")
public class Post {

    // Klucz glowny komunikatu.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post", nullable = false)
    private Long id;

    // Tytul i tresc komunikatu.
    @Column(name = "tytul", nullable = false)
    private String tytul;

    @Column(name = "tresc", nullable = false, columnDefinition = "text")
    private String tresc;

    // Flaga decyduje, czy komunikat jest widoczny publicznie.
    @Column(name = "opublikowany", nullable = false)
    private boolean opublikowany = false;

    // Data utworzenia komunikatu.
    @Column(name = "utworzono", nullable = false)
    private OffsetDateTime utworzono = OffsetDateTime.now();

    public Post() {}

    public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

    public String getTytul() { return tytul; }
    public void setTytul(String tytul) { this.tytul = tytul; }

    public String getTresc() { return tresc; }
    public void setTresc(String tresc) { this.tresc = tresc; }

    public boolean isOpublikowany() { return opublikowany; }
    public void setOpublikowany(boolean opublikowany) { this.opublikowany = opublikowany; }

    public OffsetDateTime getUtworzono() { return utworzono; }
    public void setUtworzono(OffsetDateTime utworzono) { this.utworzono = utworzono; }
}
