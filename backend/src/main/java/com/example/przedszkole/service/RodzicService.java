package com.example.przedszkole.service;

import com.example.przedszkole.model.Rodzic;
import com.example.przedszkole.model.Rola;
import com.example.przedszkole.repository.RodzicRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// Serwis zawiera logike zwiazana z rodzicami/uzytkownikami systemu.
@Service
public class RodzicService {

    // Repozytorium rodzicow komunikuje sie z baza danych.
    private final RodzicRepository rodzicRepository;

    // PasswordEncoder hashuje nowe hasla przed zapisem do bazy.
    private final PasswordEncoder passwordEncoder;

    public RodzicService(RodzicRepository rodzicRepository, PasswordEncoder passwordEncoder) {
        this.rodzicRepository = rodzicRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Pobiera wszystkich rodzicow.
    public List<Rodzic> findAll() {
        return rodzicRepository.findAll();
    }

    // Pobiera jednego rodzica po ID albo rzuca blad, gdy rodzic nie istnieje.
    public Rodzic findById(Long id) {
        return rodzicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rodzic nie istnieje"));
    }

    // Zapisuje rodzica. Ustawia domyslna role i hashuje haslo, jesli trzeba.
    public Rodzic save(Rodzic rodzic) {
        if (rodzic.getRola() == null) {
            rodzic.setRola(Rola.RODZIC);
        }

        if (rodzic.getHaslo() != null && !czyBCrypt(rodzic.getHaslo())) {
            rodzic.setHaslo(passwordEncoder.encode(rodzic.getHaslo()));
        }

        return rodzicRepository.save(rodzic);
    }

    // Tworzy nowego rodzica i pilnuje, zeby email nie byl juz zajety.
    public Rodzic createRodzic(Rodzic rodzic) {
        if (rodzicRepository.existsByEmail(rodzic.getEmail())) {
            throw new IllegalArgumentException("Uzytkownik z takim emailem juz istnieje");
        }

        rodzic.setId_rodzica(null);
        rodzic.setRola(Rola.RODZIC);
        return save(rodzic);
    }

    // Usuwa rodzica po ID.
    public void delete(Long id) {
        rodzicRepository.deleteById(id);
    }

    // Sprawdza, czy haslo wyglada juz jak hash BCrypt.
    private boolean czyBCrypt(String haslo) {
        return haslo.startsWith("$2a$") || haslo.startsWith("$2b$") || haslo.startsWith("$2y$");
    }
}
