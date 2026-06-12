package com.example.przedszkole.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Encja JPA mapowana na tabele dziecko. Reprezentuje dziecko w przedszkolu.
@Entity
@Table(name = "dziecko")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Dziecko {

    // Klucz glowny tabeli dziecko.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dziecka", nullable = false)
    private Long id_dziecka;

    // Podstawowe dane dziecka.
    @Column(name = "imie", nullable = false)
    private String imie;

    @Column(name = "nazwisko", nullable = false)
    private String nazwisko;

    @Column(name = "data_urodzenia", nullable = false)
    private LocalDate data_urodzenia;

    @Column(name = "status_pobytu", nullable = false)
    private String status_pobytu;

    // Wiele dzieci moze nalezec do jednego rodzica.
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rodzic_id_rodzica")
    private Rodzic rodzic;

    // Relacje do innych danych przypisanych do dziecka.
    @OneToMany(mappedBy = "dziecko", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Powiadomienie> powiadomienia = new ArrayList<>();

    @OneToMany(mappedBy = "dziecko", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zapis_stolowka> zapisy_stolowka = new ArrayList<>();

    @OneToMany(mappedBy = "dziecko", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zapis_zajecia> zapisy_zajecia = new ArrayList<>();

    public Dziecko() {}

    public Long getId_dziecka() { return id_dziecka; }
    public void setId_dziecka(Long id_dziecka) { this.id_dziecka = id_dziecka; }

    public String getImie() { return imie; }
    public void setImie(String imie) { this.imie = imie; }

    public String getNazwisko() { return nazwisko; }
    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }

    public LocalDate getData_urodzenia() { return data_urodzenia; }
    public void setData_urodzenia(LocalDate data_urodzenia) { this.data_urodzenia = data_urodzenia; }

    public String getStatus_pobytu() { return status_pobytu; }
    public void setStatus_pobytu(String status_pobytu) { this.status_pobytu = status_pobytu; }

    public Rodzic getRodzic() { return rodzic; }
    public void setRodzic(Rodzic rodzic) { this.rodzic = rodzic; }

    public List<Powiadomienie> getPowiadomienia() { return powiadomienia; }
    public void setPowiadomienia(List<Powiadomienie> powiadomienia) { this.powiadomienia = powiadomienia; }

    public List<Zapis_stolowka> getZapisy_stolowka() { return zapisy_stolowka; }
    public void setZapisy_stolowka(List<Zapis_stolowka> zapisy_stolowka) { this.zapisy_stolowka = zapisy_stolowka; }

    public List<Zapis_zajecia> getZapisy_zajecia() { return zapisy_zajecia; }
    public void setZapisy_zajecia(List<Zapis_zajecia> zapisy_zajecia) { this.zapisy_zajecia = zapisy_zajecia; }
}
