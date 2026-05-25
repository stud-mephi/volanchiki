package com.badminton.tournament.controller;

import com.badminton.tournament.dto.PairDTO;
import com.badminton.tournament.service.PairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pairs")
@RequiredArgsConstructor
public class PairController {

    private final PairService pairService;

    @PostMapping
    public ResponseEntity<PairDTO> create(@RequestBody Map<String, Object> body, Authentication auth) {
        Long player1Id = (Long) auth.getPrincipal();
        Long player2Id = Long.valueOf(body.get("player2Id").toString());
        String pairType = body.get("pairType").toString();
        return ResponseEntity.ok(pairService.create(player1Id, player2Id, pairType));
    }

    @GetMapping("/my")
    public ResponseEntity<List<PairDTO>> getMyPairs(Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return ResponseEntity.ok(pairService.findByUser(userId));
    }
}
