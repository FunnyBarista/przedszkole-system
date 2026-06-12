package com.example.przedszkole.controller;

import com.example.przedszkole.dto.LoginRequest;
import com.example.przedszkole.dto.LoginResponse;
import com.example.przedszkole.dto.RegisterRequest;
import com.example.przedszkole.model.Rodzic;
import com.example.przedszkole.model.Rola;
import com.example.przedszkole.repository.RodzicRepository;
import com.example.przedszkole.security.LoginAttemptService;
import com.example.przedszkole.security.JwtService;
import com.example.przedszkole.service.SecurityEventService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // JwtService wystawia token po poprawnym logowaniu/rejestracji.
    private final JwtService jwtService;
    // LoginAttemptService pilnuje limitu blednych prob logowania.
    private final LoginAttemptService loginAttemptService;
    // SecurityEventService zapisuje zdarzenia do dziennika security_event.
    private final SecurityEventService securityEventService;
    private final RodzicRepository rodzicRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            JwtService jwtService,
            LoginAttemptService loginAttemptService,
            SecurityEventService securityEventService,
            RodzicRepository rodzicRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.jwtService = jwtService;
        this.loginAttemptService = loginAttemptService;
        this.securityEventService = securityEventService;
        this.rodzicRepository = rodzicRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/test")
    public String test() {
        return "AuthController dziala";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        // Najpierw sprawdzamy blokade, zeby nie sprawdzac hasla dla konta z czasowym banem.
        if (loginAttemptService.isBlocked(request.getEmail())) {
            securityEventService.record(
                    SecurityEventService.LOGIN_BLOCKED,
                    request.getEmail(),
                    clientIp(httpRequest),
                    "Proba logowania na czasowo zablokowane konto"
            );
            return ResponseEntity.status(429).body(
                    "Zbyt wiele nieudanych prob logowania. Konto zablokowane na "
                            + loginAttemptService.getLockMinutes()
                            + " minut."
            );
        }

        Optional<Rodzic> znalezionyRodzic = rodzicRepository.findByEmail(request.getEmail());

        // Ten sam komunikat dla zlego emaila i zlego hasla utrudnia zgadywanie istniejacych kont.
        if (znalezionyRodzic.isEmpty()
                || !hasloPoprawne(request.getHaslo(), znalezionyRodzic.get().getHaslo())) {
            loginAttemptService.loginFailed(request.getEmail());
            securityEventService.record(
                    SecurityEventService.LOGIN_FAILED,
                    request.getEmail(),
                    clientIp(httpRequest),
                    "Nieprawidlowy email lub haslo"
            );
            return ResponseEntity.status(401).body("Nieprawidlowy email lub haslo");
        }

        Rodzic rodzic = znalezionyRodzic.get();
        // Udane logowanie resetuje licznik bledow i trafia do logow audytowych.
        loginAttemptService.loginSucceeded(rodzic.getEmail());
        securityEventService.record(
                SecurityEventService.LOGIN_SUCCESS,
                rodzic.getEmail(),
                clientIp(httpRequest),
                "Poprawne logowanie uzytkownika z rola " + rodzic.getRola().name()
        );
        String rola = rodzic.getRola().name();
        // W tokenie zapisujemy email i role; kolejne requesty beda uzywac go w naglowku Authorization.
        String token = jwtService.generateToken(rodzic.getEmail(), rola);

        return ResponseEntity.ok(new LoginResponse(token, rodzic.getEmail(), rola));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Email jest loginem, dlatego musi byc unikalny.
        if (rodzicRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Uzytkownik z takim emailem juz istnieje");
        }

        Rodzic rodzic = new Rodzic();
        rodzic.setImie(request.getImie());
        rodzic.setNazwisko(request.getNazwisko());
        rodzic.setEmail(request.getEmail());
        // Do bazy zapisujemy hash hasla, a nie haslo podane w formularzu.
        rodzic.setHaslo(passwordEncoder.encode(request.getHaslo()));
        rodzic.setTelefon(request.getTelefon());
        rodzic.setRola(Rola.RODZIC);

        Rodzic zapisany = rodzicRepository.save(rodzic);
        String rola = zapisany.getRola().name();
        String token = jwtService.generateToken(zapisany.getEmail(), rola);

        return ResponseEntity.ok(new LoginResponse(token, zapisany.getEmail(), rola));
    }

    private boolean hasloPoprawne(String hasloZFormularza, String hasloZBazy) {
        if (hasloZBazy == null) {
            return false;
        }

        // Nowe hasla sa w BCrypt. Warunek ponizej rozpoznaje format hasha BCrypt po prefiksie.
        if (hasloZBazy.startsWith("$2a$") || hasloZBazy.startsWith("$2b$") || hasloZBazy.startsWith("$2y$")) {
            return passwordEncoder.matches(hasloZFormularza, hasloZBazy);
        }

        // Fallback dla starych danych zapisanych jeszcze jako zwykly tekst.
        return hasloZBazy.equals(hasloZFormularza);
    }

    private String clientIp(HttpServletRequest request) {
        // Gdy aplikacja stoi za proxy, prawdziwy adres klienta czesto jest w X-Forwarded-For.
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}
