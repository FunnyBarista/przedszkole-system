package com.example.przedszkole.repository;

import com.example.przedszkole.model.Aktualnosc;
import org.springframework.data.jpa.repository.JpaRepository;

// Repozytorium daje gotowe metody CRUD dla encji Aktualnosc.
public interface AktualnoscRepository extends JpaRepository<Aktualnosc, Long> {
}
