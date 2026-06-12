package com.example.przedszkole.service;

import com.example.przedszkole.model.Uwaga;
import com.example.przedszkole.repository.UwagaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

// Serwis zawiera logike zwiazana z uwagami dla dzieci.
@Service
public class UwagaService {

    // Repozytorium wykonuje operacje na tabeli uwag.
    private final UwagaRepository uwagaRepository;

    public UwagaService(UwagaRepository uwagaRepository) {
        this.uwagaRepository = uwagaRepository;
    }

    // Pobiera wszystkie uwagi, np. dla nauczyciela, dyrekcji albo admina.
    public List<Uwaga> pobierzWszystkie() {
        return uwagaRepository.findAll();
    }

    // Pobiera uwagi przypisane do konkretnego dziecka.
    public List<Uwaga> pobierzDlaDziecka(Long idDziecka) {
        return uwagaRepository.findAll().stream()
                .filter(u -> u.getDziecko() != null
                        && u.getDziecko().getId_dziecka().equals(idDziecka))
                .toList();
    }

    // Dodaje nowa uwage do bazy danych.
    public Uwaga dodaj(Uwaga uwaga) {
        return uwagaRepository.save(uwaga);
    }
}
