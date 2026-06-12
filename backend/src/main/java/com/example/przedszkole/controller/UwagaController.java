package com.example.przedszkole.controller;

import com.example.przedszkole.model.Uwaga;
import com.example.przedszkole.service.UwagaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/uwagi") // 
public class UwagaController {
    private final UwagaService uwagaService;

    public UwagaController(UwagaService uwagaService) {
        this.uwagaService = uwagaService;
    }

    // GET /api/uwagi/rodzic/{id} - Widok dla rodzica [cite: 13, 28]
    @GetMapping("/dziecko/{idDziecka}")
public List<Uwaga> pobierz(@PathVariable Long idDziecka) {
    return uwagaService.pobierzDlaDziecka(idDziecka);
}
    // GET /api/uwagi - Widok dla nauczyciela 
    @GetMapping
    public List<Uwaga> getWszystkieUwagi() {
        return uwagaService.pobierzWszystkie();
    }

    // POST /api/uwagi - Wystawianie uwagi [cite: 14, 37]
    @PostMapping
    public Uwaga wystawUwage(@RequestBody Uwaga uwaga) {
        return uwagaService.dodaj(uwaga);
    }
}