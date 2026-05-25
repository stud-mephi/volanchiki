package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 10)
    private String gender;  // MALE, FEMALE

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    private String city;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "first_tournament_date")
    private LocalDate firstTournamentDate;

    @Column(name = "tournaments_played")
    private Integer tournamentsPlayed = 0;

    @Column(name = "last_active_date")
    private LocalDate lastActiveDate;

    // НОВОЕ ПОЛЕ - РОЛЬ
    @Column(nullable = false, length = 20)
    private String role = "PLAYER";  // PLAYER или ORGANIZER

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (role == null) {
            role = "PLAYER";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public int getAge() {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public boolean isNewbie() {
        return tournamentsPlayed != null && tournamentsPlayed <= 5;
    }
    
    // Проверка роли
    public boolean isOrganizer() {
        return "ORGANIZER".equals(role);
    }
    
    public boolean isPlayer() {
        return "PLAYER".equals(role);
    }
}
