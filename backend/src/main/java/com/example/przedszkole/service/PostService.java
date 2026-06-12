package com.example.przedszkole.service;

import com.example.przedszkole.dto.PostRequest;
import com.example.przedszkole.model.Post;
import com.example.przedszkole.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Serwis zawiera logike komunikatow widocznych publicznie i administracyjnie.
@Service
public class PostService {

    // Repozytorium wykonuje operacje na tabeli Post.
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Zwraca tylko opublikowane komunikaty, czyli widoczne dla niezalogowanych.
    public List<Post> listaPubliczna() {
        return postRepository.findAllByOpublikowanyTrueOrderByUtworzonoDesc();
    }

    // Zwraca wszystkie komunikaty, takze robocze i nieopublikowane.
    public List<Post> listaAdmin() {
        return postRepository.findAll();
    }

    // Pobiera jeden komunikat po ID albo rzuca blad, gdy go nie ma.
    public Post pobierz(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post nie istnieje"));
    }

    // Tworzy nowy komunikat na podstawie DTO z kontrolera.
    public Post utworz(PostRequest req) {
        Post p = new Post();
        p.setTytul(req.tytul);
        p.setTresc(req.tresc);
        p.setOpublikowany(req.opublikowany != null && req.opublikowany);
        return postRepository.save(p);
    }

    // Edytuje tylko pola przekazane w request. Puste pola zostaja bez zmian.
    public Post edytuj(Long id, PostRequest req) {
        Post p = pobierz(id);
        if (req.tytul != null) p.setTytul(req.tytul);
        if (req.tresc != null) p.setTresc(req.tresc);
        if (req.opublikowany != null) p.setOpublikowany(req.opublikowany);
        return postRepository.save(p);
    }

    // Usuwa komunikat po ID.
    public void usun(Long id) {
        postRepository.deleteById(id);
    }
}
