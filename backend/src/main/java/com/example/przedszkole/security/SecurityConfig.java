package com.example.przedszkole.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    // Filtr JWT jest uruchamiany przy kazdym zapytaniu i probuje odczytac token z naglowka Authorization.
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // CORS pozwala frontendowi z Vite (localhost:5173) wysylac zapytania do backendu.
                .cors(cors -> {})
                // API korzysta z JWT, a nie z formularzy i sesji przegladarki, dlatego CSRF nie jest tu potrzebny.
                .csrf(csrf -> csrf.disable())
                // Wylaczamy domyslne mechanizmy logowania Springa, bo logowanie obsluguje AuthController.
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                // STATELESS oznacza, ze backend nie trzyma sesji uzytkownika; kazde zapytanie musi miec token JWT.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Endpointy publiczne: logowanie/rejestracja, dokumentacja API i strona startowa.
                        .requestMatchers(
                                "/",
                                "/error",
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        // Publiczny odczyt komunikatow i jadlospisu - uzytkownik nie musi byc zalogowany.
                        .requestMatchers(HttpMethod.GET, "/api/posts", "/api/posts/**", "/api/posilki", "/api/posilki/**").permitAll()
                        // Dodawanie i edycja jadlospisu jest dostepna dla pracownikow z odpowiednimi rolami.
                        .requestMatchers(HttpMethod.POST, "/api/posilki")
                                .hasAnyRole("NAUCZYCIEL", "DYREKCJA", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/posilki/**")
                                .hasAnyRole("NAUCZYCIEL", "DYREKCJA", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/posilki/**")
                                .hasAnyRole("DYREKCJA", "ADMIN")
                        // Uwagi o dzieciach sa chronione - rodzic widzi swoje dane, a kadra ma szerszy dostep.
                        .requestMatchers(HttpMethod.GET, "/api/uwagi/dziecko/**")
                                .hasAnyRole("RODZIC", "NAUCZYCIEL", "DYREKCJA", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/uwagi")
                                .hasAnyRole("NAUCZYCIEL", "DYREKCJA", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/uwagi")
                                .hasAnyRole("NAUCZYCIEL", "DYREKCJA", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/rodzice/*/dzieci")
                                .hasAnyRole("RODZIC", "NAUCZYCIEL", "DYREKCJA", "ADMIN")
                        // Zarzadzanie rodzicami jest ograniczone do dyrekcji i administratora.
                        .requestMatchers(HttpMethod.GET, "/api/rodzice/**")
                                .hasAnyRole("DYREKCJA", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/rodzice/**")
                                .hasAnyRole("DYREKCJA", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/rodzice/**")
                                .hasAnyRole("DYREKCJA", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/rodzice/**")
                                .hasRole("ADMIN")

                        // Panel administracyjny, w tym logi security, nie jest dostepny dla zwyklych rodzicow.
                        .requestMatchers("/api/admin/**").hasAnyRole("DYREKCJA", "ADMIN")
                        // Wszystkie pozostale endpointy wymagaja poprawnego tokena JWT.
                        .anyRequest().authenticated()
                )
                // Filtr JWT musi wykonac sie przed standardowym filtrem logowania Spring Security.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Dozwolony origin frontendu uruchamianego lokalnie przez Vite.
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        // Metody HTTP, ktore frontend moze wysylac do API.
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Authorization jest potrzebny do przesylania tokena Bearer JWT.
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt zapisuje hasla jako hashe, dzieki czemu w bazie nie ma jawnego hasla uzytkownika.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Uzytkownikow nie ladujemy klasycznym UserDetailsService, bo to JWT dostarcza email i role.
        return username -> {
            throw new UsernameNotFoundException("Uzytkownik obslugiwany przez JWT/RBAC: " + username);
        };
    }
}




