package com.badminton.tournament.service;

import com.badminton.tournament.dto.AuthRequestDTO;
import com.badminton.tournament.dto.AuthResponseDTO;
import com.badminton.tournament.dto.RegisterRequestDTO;
import com.badminton.tournament.dto.UserDTO;
import com.badminton.tournament.exception.BadRequestException;
import com.badminton.tournament.exception.ResourceNotFoundException;
import com.badminton.tournament.model.User;
import com.badminton.tournament.repository.UserRepository;
import com.badminton.tournament.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email уже занят");
        }
        if (userRepository.existsByNickname(dto.getNickname())) {
            throw new BadRequestException("Никнейм уже занят");
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setBirthDate(dto.getBirthDate());
        user.setGender(dto.getGender());
        user.setPhone(dto.getPhone());
        user.setCity(dto.getCity());
        user.setIsActive(true);
        user.setTournamentsPlayed(0);
        
        // НОВОЕ: устанавливаем роль из запроса
        String role = dto.getRole();
        if (role == null || role.isEmpty()) {
            role = "PLAYER";  // по умолчанию игрок
        }
        user.setRole(role);

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        return AuthResponseDTO.builder()
                .userId(user.getId())
                .token(token)
                .fullName(user.getFullName())
                .nickname(user.getNickname())
                .role(user.getRole())  // возвращаем роль
                .build();
    }

    public AuthResponseDTO login(AuthRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Неверный email или пароль"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Неверный email или пароль");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        return AuthResponseDTO.builder()
                .userId(user.getId())
                .token(token)
                .fullName(user.getFullName())
                .nickname(user.getNickname())
                .role(user.getRole())  // возвращаем роль
                .build();
    }

    public UserDTO getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        return toDTO(user);
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .city(user.getCity())
                .gender(user.getGender())
                .age(user.getAge())
                .isActive(user.getIsActive())
                .isNewbie(user.isNewbie())
                .tournamentsPlayed(user.getTournamentsPlayed())
                .firstTournamentDate(user.getFirstTournamentDate())
                .lastActiveDate(user.getLastActiveDate())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

