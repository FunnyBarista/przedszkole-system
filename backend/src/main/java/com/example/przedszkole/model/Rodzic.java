package com.example.przedszkole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Encja JPA mapowana na tabele rodzic. Reprezentuje rodzica/uzytkownika systemu.
@Entity
@Table(name = "rodzic")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Rodzic {

    // Klucz glowny tabeli. Baza danych sama nadaje kolejne ID.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rodzica", nullable = false)
    private Long id_rodzica;

    // Podstawowe dane rodzica.
    @Column(name = "imie", nullable = false, length = 50)
    private String imie;

    @Column(name = "nazwisko", nullable = false, length = 50)
    private String nazwisko;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    // Haslo jest przyjmowane w JSON-ie, ale nie jest zwracane w odpowiedziach API.
    @Column(name = "haslo", nullable = false, length = 200)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String haslo;

    @Column(name = "telefon", nullable = false, length = 20)
    private String telefon;

    // Rola steruje uprawnieniami uzytkownika w SecurityConfig.
    @Enumerated(EnumType.STRING)
    @Column(name = "rola", nullable = false, length = 30)
    private Rola rola = Rola.RODZIC;

    // Jeden rodzic moze miec wiele dzieci.
    @JsonManagedReference
    @OneToMany(mappedBy = "rodzic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dziecko> dzieci = new ArrayList<>();


    public Rodzic() {}

    public Long getId_rodzica() { return id_rodzica; }
    public void setId_rodzica(Long id_rodzica) { this.id_rodzica = id_rodzica; }

    public String getImie() { return imie; }
    public void setImie(String imie) { this.imie = imie; }

    public String getNazwisko() { return nazwisko; }
    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHaslo() { return haslo; }
    public void setHaslo(String haslo) { this.haslo = haslo; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public Rola getRola() { return rola; }
    public void setRola(Rola rola) { this.rola = rola; }

    public List<Dziecko> getDzieci() { return dzieci; }
    public void setDzieci(List<Dziecko> dzieci) { this.dzieci = dzieci; }
}
