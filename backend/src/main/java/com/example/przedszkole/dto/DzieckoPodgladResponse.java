package com.example.przedszkole.dto;

import com.example.przedszkole.model.Dziecko;
import com.example.przedszkole.model.Rodzic;

import java.time.LocalDate;

// DTO do administracyjnego podgladu dziecka razem z podstawowymi danymi rodzica.
public class DzieckoPodgladResponse {

    // Dane dziecka zwracane do frontendu.
    private Long id;
    private String imie;
    private String nazwisko;
    private LocalDate dataUrodzenia;
    private String statusPobytu;

    // Dane rodzica przypisanego do dziecka.
    private Long idRodzica;
    private String rodzic;
    private String emailRodzica;

    // Konstruktor przepisuje tylko te pola, ktore sa potrzebne w widoku podgladu.
    public DzieckoPodgladResponse(Dziecko dziecko) {
        this.id = dziecko.getId_dziecka();
        this.imie = dziecko.getImie();
        this.nazwisko = dziecko.getNazwisko();
        this.dataUrodzenia = dziecko.getData_urodzenia();
        this.statusPobytu = dziecko.getStatus_pobytu();

        Rodzic rodzicDziecka = dziecko.getRodzic();
        if (rodzicDziecka != null) {
            this.idRodzica = rodzicDziecka.getId_rodzica();
            this.rodzic = rodzicDziecka.getImie() + " " + rodzicDziecka.getNazwisko();
            this.emailRodzica = rodzicDziecka.getEmail();
        }
    }

    public Long getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public LocalDate getDataUrodzenia() {
        return dataUrodzenia;
    }

    public String getStatusPobytu() {
        return statusPobytu;
    }

    public Long getIdRodzica() {
        return idRodzica;
    }

    public String getRodzic() {
        return rodzic;
    }

    public String getEmailRodzica() {
        return emailRodzica;
    }
}
