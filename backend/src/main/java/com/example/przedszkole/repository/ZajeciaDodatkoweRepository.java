package com.example.przedszkole.repository;

import com.example.przedszkole.model.Zajecia_dodatkowe;
import org.springframework.data.jpa.repository.JpaRepository;

// Repozytorium zajec dodatkowych dostepnych w przedszkolu.
public interface ZajeciaDodatkoweRepository extends JpaRepository<Zajecia_dodatkowe, Long> {
}
