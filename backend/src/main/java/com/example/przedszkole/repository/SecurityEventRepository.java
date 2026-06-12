package com.example.przedszkole.repository;

import com.example.przedszkole.model.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repozytorium logow bezpieczenstwa zapisywanych przy logowaniu.
public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Long> {
    // Pobiera 100 najnowszych zdarzen, zeby administrator widzial ostatnia aktywnosc.
    List<SecurityEvent> findTop100ByOrderByCreatedAtDesc();
}
