package com.badminton.tournament.controller;

import com.badminton.tournament.dto.MatchDTO;
import com.badminton.tournament.dto.MatchResultDTO;
import com.badminton.tournament.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<MatchDTO>> getByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(matchService.findByTournament(tournamentId));
    }

    @PutMapping("/{id}/result")
    public ResponseEntity<MatchDTO> submitResult(@PathVariable Long id,
                                                  @Valid @RequestBody MatchResultDTO dto) {
        dto.setMatchId(id);
        return ResponseEntity.ok(matchService.submitResult(dto));
    }
}
