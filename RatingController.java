package com.badminton.tournament.controller;

import com.badminton.tournament.dto.RatingDTO;
import com.badminton.tournament.dto.RatingHistoryDTO;
import com.badminton.tournament.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<RatingDTO>> getTop(
            @RequestParam(defaultValue = "SINGLES") String categoryType,
            @RequestParam(defaultValue = "MALE") String categoryGender,
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "0") int minGames) {
        return ResponseEntity.ok(ratingService.getTopPlayers(categoryType, categoryGender, limit, minGames));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<RatingHistoryDTO>> getHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(ratingService.getUserHistory(userId));
    }
}
