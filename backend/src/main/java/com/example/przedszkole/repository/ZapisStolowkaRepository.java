package com.example.przedszkole.repository;

import com.example.przedszkole.model.Zapis_stolowka;
import org.springframework.data.jpa.repository.JpaRepository;

// Repozytorium zapisow dzieci na stolowke.
public interface ZapisStolowkaRepository extends JpaRepository<Zapis_stolowka, Long> {
}
