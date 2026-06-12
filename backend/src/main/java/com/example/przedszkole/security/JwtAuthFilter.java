package com.example.przedszkole.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    // JwtService zna sekret podpisu i potrafi sprawdzic, czy token jest prawdziwy oraz nie wygasl.
    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Frontend wysyla token w naglowku: Authorization: Bearer <token>.
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Odcinamy prefix "Bearer ", zostaje sam token JWT.
            String token = authHeader.substring(7);

            if (jwtService.isTokenValid(token)) {

                // Email jest zapisany jako subject tokena, a rola jako osobny claim "rola".
                String email = jwtService.extractEmail(token);
                String rola = jwtService.extractRola(token);

                // Spring Security oczekuje roli w formacie ROLE_NAZWA, np. ROLE_ADMIN.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + rola))
                        );

                // Od tego momentu kontrolery i reguly hasRole/hasAnyRole widza uzytkownika jako zalogowanego.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Przekazujemy zapytanie dalej, nawet jesli tokenu nie bylo; wtedy decyzje podejmuje SecurityConfig.
        filterChain.doFilter(request, response);
    }
}
