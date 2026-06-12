package com.example.przedszkole.controller;

import com.example.przedszkole.model.Rodzic;
import com.example.przedszkole.service.RodzicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rodzice")
public class RodzicController {

    // Serwis wykonuje operacje na rodzicach i dba m.in. o hashowanie hasla.
    private final RodzicService rodzicService;

    public RodzicController(RodzicService rodzicService) {
        this.rodzicService = rodzicService;
    }

    // GET /api/rodzice
    // Zwraca liste wszystkich rodzicow. Dostep jest ograniczony w SecurityConfig.
    @GetMapping
    public List<Rodzic> getAll() {
        return rodzicService.findAll();
    }

    // GET /api/rodzice/{id}
    // Pobiera jednego rodzica po identyfikatorze z adresu URL.
    @GetMapping("/{id}")
    public Rodzic getById(@PathVariable Long id) {
        return rodzicService.findById(id);
    }

    // POST /api/rodzice
    // Tworzy nowego rodzica. ResponseEntity pozwala zwrocic 200 albo 400 z opisem bledu.
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Rodzic rodzic) {
        try {
            return ResponseEntity.ok(rodzicService.createRodzic(rodzic));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/rodzice/{id}
    // Aktualizuje dane rodzica. ID z adresu ustawiamy na obiekcie przed zapisem.
    @PutMapping("/{id}")
    public Rodzic update(@PathVariable Long id, @RequestBody Rodzic rodzic) {
        rodzic.setId_rodzica(id);
        return rodzicService.save(rodzic);
    }

    // DELETE /api/rodzice/{id}
    // Usuwa rodzica o podanym ID. W SecurityConfig dostep do DELETE ma tylko ADMIN.
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rodzicService.delete(id);
    }
}
