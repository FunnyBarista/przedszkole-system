package com.example.przedszkole.service;

import com.example.przedszkole.model.Jadlospis;
import com.example.przedszkole.repository.JadlospisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Serwis zawiera logike zwiazana z jadlospisem.
@Service
public class JadlospisService {

    // Repozytorium komunikuje sie z tabela jadlospisu w bazie.
    private final JadlospisRepository jadlospisRepository;

    public JadlospisService(JadlospisRepository jadlospisRepository) {
        this.jadlospisRepository = jadlospisRepository;
    }

    // Pobiera wszystkie wpisy jadlospisu.
    public List<Jadlospis> pobierzWszystkie() {
        return jadlospisRepository.findAll();
    }

    // Pobiera jeden wpis po ID albo zwraca blad, jesli wpis nie istnieje.
    public Jadlospis pobierz(Long id) {
        return jadlospisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jadlospis nie istnieje"));
    }

    // Zapisuje nowy wpis lub aktualizuje istniejacy wpis jadlospisu.
    public Jadlospis zapisz(Jadlospis jadlospis) {
        return jadlospisRepository.save(jadlospis);
    }

    // Usuwa wpis jadlospisu po ID.
    public void usun(Long id) {
        jadlospisRepository.deleteById(id);
    }
}
