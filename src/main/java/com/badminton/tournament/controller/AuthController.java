package com.badminton.tournament.controller;

import com.badminton.tournament.dto.AuthRequestDTO;
import com.badminton.tournament.dto.AuthResponseDTO;
import com.badminton.tournament.dto.RegisterRequestDTO;
import com.badminton.tournament.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO dto) {
        return ResponseEntity.ok(userService.login(dto));
    }
}

