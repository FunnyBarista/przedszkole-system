package com.example.przedszkole.controller;

import com.example.przedszkole.dto.PostRequest;
import com.example.przedszkole.model.Post;
import com.example.przedszkole.service.PostService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    // Serwis zawiera logike zwiazana z komunikatami/aktualnosciami.
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // GET /api/posts
    // Publiczna lista opublikowanych komunikatow widoczna bez logowania.
    @GetMapping("/posts")
    public List<Post> publicznePosty() {
        return postService.listaPubliczna();
    }

    // GET /api/posts/{id}
    // Publiczny podglad jednego komunikatu. Nieopublikowany post nie jest zwracany publicznie.
    @GetMapping("/posts/{id}")
    public Post publicznyPost(@PathVariable Long id) {
        Post post = postService.pobierz(id);
        if (!post.isOpublikowany()) {
            throw new IllegalArgumentException("Post nie jest opublikowany");
        }
        return post;
    }

    // GET /api/admin/posts
    // Administracyjna lista wszystkich komunikatow, takze nieopublikowanych.
    @GetMapping("/admin/posts")
    public List<Post> adminLista() {
        return postService.listaAdmin();
    }

    // GET /api/admin/posts/{id}
    // Administracyjny podglad jednego komunikatu po ID.
    @GetMapping("/admin/posts/{id}")
    public Post adminPobierz(@PathVariable Long id) {
        return postService.pobierz(id);
    }

    // POST /api/admin/posts
    // Tworzy nowy komunikat na podstawie danych przeslanych w body.
    @PostMapping("/admin/posts")
    public Post adminUtworz(@RequestBody PostRequest req) {
        return postService.utworz(req);
    }

    // PUT /api/admin/posts/{id}
    // Edytuje istniejacy komunikat o identyfikatorze z adresu URL.
    @PutMapping("/admin/posts/{id}")
    public Post adminEdytuj(@PathVariable Long id, @RequestBody PostRequest req) {
        return postService.edytuj(id, req);
    }

    // DELETE /api/admin/posts/{id}
    // Usuwa komunikat o podanym ID.
    @DeleteMapping("/admin/posts/{id}")
    public void adminUsun(@PathVariable Long id) {
        postService.usun(id);
    }
}
