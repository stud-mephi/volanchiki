package com.badminton.tournament.controller;

import com.badminton.tournament.dto.TournamentDTO;
import com.badminton.tournament.dto.TournamentRequestDTO;
import com.badminton.tournament.service.TournamentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAll(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(tournamentService.findAll(city, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TournamentDTO> create(@Valid @RequestBody TournamentRequestDTO dto,
                                                 Authentication auth) {
        Long organizerId = (Long) auth.getPrincipal();
        return ResponseEntity.ok(tournamentService.create(dto, organizerId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        tournamentService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
