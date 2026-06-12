package com.example.przedszkole.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "status", "Backend przedszkole dziala",
                "frontend", "http://localhost:5173",
                "swagger", "http://localhost:8080/swagger-ui.html"
        );
    }
}
