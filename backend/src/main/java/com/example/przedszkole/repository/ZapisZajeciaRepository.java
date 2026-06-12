package com.example.przedszkole.repository;

import com.example.przedszkole.model.Zapis_zajecia;
import org.springframework.data.jpa.repository.JpaRepository;

// Repozytorium zapisow dzieci na zajecia dodatkowe.
public interface ZapisZajeciaRepository extends JpaRepository<Zapis_zajecia, Long> {
}
