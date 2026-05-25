package com.badminton.tournament.controller;

import com.badminton.tournament.dto.RegistrationRequestDTO;
import com.badminton.tournament.dto.RegistrationResponseDTO;
import com.badminton.tournament.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> register(
            @Valid @RequestBody RegistrationRequestDTO dto,
            Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return ResponseEntity.ok(registrationService.register(userId, dto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<RegistrationResponseDTO>> getMyRegistrations(Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return ResponseEntity.ok(registrationService.findByUser(userId));
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<RegistrationResponseDTO>> getByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(registrationService.findByTournament(tournamentId));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable Long id) {
        registrationService.confirm(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long id) {
        registrationService.reject(id);
        return ResponseEntity.ok().build();
    }
}
