package com.example.przedszkole.repository;

import com.example.przedszkole.model.Uwaga;
import org.springframework.data.jpa.repository.JpaRepository;

// Repozytorium dla uwag przypisanych do dzieci.
public interface UwagaRepository extends JpaRepository<Uwaga, Long> {
}
