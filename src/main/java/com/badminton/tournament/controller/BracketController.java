package com.badminton.tournament.controller;

import com.badminton.tournament.dto.BracketMatchDTO;
import com.badminton.tournament.service.BracketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brackets")
@RequiredArgsConstructor
public class BracketController {

    private final BracketService bracketService;

    @GetMapping("/{tournamentId}/{categoryId}")
    public ResponseEntity<List<BracketMatchDTO>> getBracket(
            @PathVariable Long tournamentId,
            @PathVariable Integer categoryId) {
        return ResponseEntity.ok(bracketService.getBracket(tournamentId, categoryId));
    }

    @PostMapping("/{tournamentId}/{categoryId}/generate")
    public ResponseEntity<List<BracketMatchDTO>> generateBracket(
            @PathVariable Long tournamentId,
            @PathVariable Integer categoryId) {
        return ResponseEntity.ok(bracketService.generateBracket(tournamentId, categoryId));
    }
}
