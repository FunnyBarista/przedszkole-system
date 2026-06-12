package com.example.przedszkole.service;

import com.example.przedszkole.dto.DzieckoPodgladResponse;
import com.example.przedszkole.model.Dziecko;
import com.example.przedszkole.repository.DzieckoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Serwis zawiera logike aplikacji zwiazana z dziecmi.
// Kontroler wywoluje ten serwis, a serwis korzysta z repozytorium.
@Service
public class DzieckoService {

    // Repozytorium wykonuje operacje na tabeli dziecko.
    private final DzieckoRepository dzieckoRepository;

    public DzieckoService(DzieckoRepository dzieckoRepository) {
        this.dzieckoRepository = dzieckoRepository;
    }

    // Pobiera dzieci przypisane do rodzica po ID rodzica.
    public List<Dziecko> findDzieciRodzica(Long idRodzica) {
        return dzieckoRepository.findByRodzicId(idRodzica);
    }

    // Pobiera dzieci zalogowanego rodzica po emailu zapisanym w tokenie JWT.
    public List<Dziecko> findDzieciRodzica(String email) {
        return dzieckoRepository.findByRodzicEmail(email);
    }

    // Pobiera wszystkie dzieci, np. dla widoku administracyjnego.
    public List<Dziecko> findAll() {
        return dzieckoRepository.findAll();
    }

    // Tworzy specjalny widok dziecka razem z danymi rodzica.
    @Transactional(readOnly = true)
    public List<DzieckoPodgladResponse> findAllPodglad() {
        return dzieckoRepository.findAll().stream()
                .map(DzieckoPodgladResponse::new)
                .toList();
    }

    // Pobiera jedno dziecko po ID albo rzuca blad, jesli go nie ma.
    public Dziecko findById(Long id) {
        return dzieckoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dziecko nie istnieje"));
    }

    // Zapisuje nowe dziecko albo aktualizuje istniejace.
    public Dziecko save(Dziecko dziecko) {
        return dzieckoRepository.save(dziecko);
    }

    // Usuwa dziecko po ID.
    public void deleteById(Long id) {
        dzieckoRepository.deleteById(id);
    }
}
