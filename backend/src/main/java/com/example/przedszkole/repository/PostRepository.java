package com.example.przedszkole.repository;

import com.example.przedszkole.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repozytorium komunikatow. Spring Data JPA tworzy implementacje automatycznie.
public interface PostRepository extends JpaRepository<Post, Long> {
    // Pobiera tylko opublikowane komunikaty i sortuje je od najnowszych.
    List<Post> findAllByOpublikowanyTrueOrderByUtworzonoDesc();
}
