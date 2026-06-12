package com.example.przedszkole.repository;

import com.example.przedszkole.model.Dziecko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Repozytorium laczy encje Dziecko z baza danych.
// JpaRepository daje gotowe metody: findAll, findById, save, deleteById.
public interface DzieckoRepository extends JpaRepository<Dziecko, Long> {

    // Wlasne zapytanie: pobiera dzieci przypisane do rodzica po jego ID.
    @Query("select d from Dziecko d where d.rodzic.id_rodzica = :idRodzica")
    List<Dziecko> findByRodzicId(@Param("idRodzica") Long idRodzica);

    // Wlasne zapytanie: pobiera dzieci zalogowanego rodzica po jego emailu.
    @Query("select d from Dziecko d where d.rodzic.email = :email")
    List<Dziecko> findByRodzicEmail(@Param("email") String email);
}
