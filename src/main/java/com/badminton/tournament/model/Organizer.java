package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "organizers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organizer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;
    
    @Column(nullable = false)
    private String city;
    
    private String description;
    private String website;
    
    @Column(name = "logo_url")
    private String logoUrl;
    
    private String inn;
    
    @Column(name = "is_verified")
    private Boolean isVerified = false;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    // ===== Добавленные поля =====
    @Column(name = "verification_token")
    private String verificationToken;
    
    @Column(name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;

    @Column(name = "reset_token")
    private String resetToken;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    // ============================
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
