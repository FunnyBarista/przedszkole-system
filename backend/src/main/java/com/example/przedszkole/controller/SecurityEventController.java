package com.example.przedszkole.controller;

import com.example.przedszkole.model.SecurityEvent;
import com.example.przedszkole.service.SecurityEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/security-events")
public class SecurityEventController {

    private final SecurityEventService securityEventService;

    public SecurityEventController(SecurityEventService securityEventService) {
        this.securityEventService = securityEventService;
    }

    @GetMapping
    public List<SecurityEvent> latestEvents() {
        return securityEventService.latestEvents();
    }
}
