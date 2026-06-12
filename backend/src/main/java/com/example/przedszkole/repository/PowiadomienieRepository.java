package com.example.przedszkole.repository;

import com.example.przedszkole.model.Powiadomienie;
import org.springframework.data.jpa.repository.JpaRepository;

// Repozytorium dla powiadomien przypisanych do dzieci.
public interface PowiadomienieRepository extends JpaRepository<Powiadomienie, Long> {
}
