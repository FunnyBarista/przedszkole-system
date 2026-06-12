package com.example.przedszkole.controller;

import com.example.przedszkole.dto.DzieckoPodgladResponse;
import com.example.przedszkole.dto.DzieckoStatusRequest;
import com.example.przedszkole.model.Dziecko;
import com.example.przedszkole.service.DzieckoService;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/api")
public class DzieckoController {

    private final DzieckoService dzieckoService;

    public DzieckoController(DzieckoService dzieckoService) {
        this.dzieckoService = dzieckoService;
    }

    @GetMapping("/rodzice/{idRodzica}/dzieci")
    public List<Dziecko> getDzieciRodzica(@PathVariable Long idRodzica) {
        return dzieckoService.findDzieciRodzica(idRodzica);
    }

    @GetMapping("/rodzice/me/dzieci")
    public List<Dziecko> getMojeDzieci(Authentication authentication) {
        return dzieckoService.findDzieciRodzica(authentication.getName());
    }

    @GetMapping("/admin/dzieci")
    public List<Dziecko> listaWszystkichDzieci() {
        return dzieckoService.findAll();
    }

    @GetMapping("/admin/dzieci/podglad")
    public List<DzieckoPodgladResponse> listaDzieciZRodzicami() {
        return dzieckoService.findAllPodglad();
    }

    @GetMapping("/dzieci/podglad")
    public List<DzieckoPodgladResponse> listaDzieciDlaPracownikow() {
        return dzieckoService.findAllPodglad();
    }

    @PutMapping("/dzieci/{id}/status")
    public DzieckoPodgladResponse zmienStatusPobytu(@PathVariable Long id, @RequestBody DzieckoStatusRequest request) {
        return new DzieckoPodgladResponse(dzieckoService.updateStatusPobytu(id, request.getStatusPobytu()));
    }

    @GetMapping("/admin/dzieci/{id}")
    public Dziecko pobierzDziecko(@PathVariable Long id) {
        return dzieckoService.findById(id);
    }

    @PostMapping("/admin/dzieci")
    public Dziecko dodajDziecko(@RequestBody Dziecko dziecko) {
        return dzieckoService.save(dziecko);
    }

    @PutMapping("/admin/dzieci/{id}")
    public Dziecko edytujDziecko(@PathVariable Long id, @RequestBody Dziecko dziecko) {
        dziecko.setId_dziecka(id);
        return dzieckoService.save(dziecko);
    }

    @DeleteMapping("/admin/dzieci/{id}")
    public void usunDziecko(@PathVariable Long id) {
        dzieckoService.deleteById(id);
    }
}
