package com.example.przedszkole.controller;

import com.example.przedszkole.model.Jadlospis;
import com.example.przedszkole.service.JadlospisService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posilki")
public class JadlospisController {

    // Serwis wykonuje wlasciwe operacje na jadlospisie i bazie danych.
    private final JadlospisService jadlospisService;

    public JadlospisController(JadlospisService jadlospisService) {
        this.jadlospisService = jadlospisService;
    }

    // GET /api/posilki
    // Zwraca liste wszystkich wpisow jadlospisu. Ten endpoint jest publiczny.
    @GetMapping
    public List<Jadlospis> getJadlospis() {
        return jadlospisService.pobierzWszystkie();
    }

    // GET /api/posilki/{id}
    // Pobiera jeden wpis jadlospisu po identyfikatorze z adresu URL.
    @GetMapping("/{id}")
    public Jadlospis getJadlospisById(@PathVariable Long id) {
        return jadlospisService.pobierz(id);
    }

    // POST /api/posilki
    // Dodaje nowy wpis jadlospisu na podstawie danych JSON przeslanych w body.
    @PostMapping
    public Jadlospis dodajJadlospis(@RequestBody Jadlospis jadlospis) {
        return jadlospisService.zapisz(jadlospis);
    }

    // PUT /api/posilki/{id}
    // Aktualizuje istniejacy wpis. ID z adresu ustawiamy na obiekcie przed zapisem.
    @PutMapping("/{id}")
    public Jadlospis edytujJadlospis(@PathVariable Long id, @RequestBody Jadlospis jadlospis) {
        jadlospis.setJadlospis_id(id);
        return jadlospisService.zapisz(jadlospis);
    }

    // DELETE /api/posilki/{id}
    // Usuwa wpis jadlospisu o podanym identyfikatorze.
    @DeleteMapping("/{id}")
    public void usunJadlospis(@PathVariable Long id) {
        jadlospisService.usun(id);
    }
}
