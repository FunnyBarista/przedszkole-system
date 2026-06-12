package com.example.przedszkole.repository;

import com.example.przedszkole.model.Rodzic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repozytorium rodzicow/uzytkownikow systemu.
// Uzywane m.in. przy logowaniu, rejestracji i zarzadzaniu rodzicami.
public interface RodzicRepository extends JpaRepository<Rodzic, Long> {
    // Szuka uzytkownika po emailu. Optional oznacza: moze znalezc, ale moze byc pusto.
    Optional<Rodzic> findByEmail(String email);

    // Sprawdza, czy email jest juz zajety, np. podczas rejestracji.
    boolean existsByEmail(String email);
}
