package com.example.przedszkole.repository;

import com.example.przedszkole.model.Jadlospis;
import org.springframework.data.jpa.repository.JpaRepository;

// Repozytorium do odczytu, zapisu, edycji i usuwania wpisow jadlospisu.
public interface JadlospisRepository extends JpaRepository<Jadlospis, Long> {
}
