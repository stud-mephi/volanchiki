package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private User partner;

    @Column(name = "team_name")
    private String teamName;

    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, CANCELLED, REJECTED, WAITLISTED

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    private String comment;

    @Column(name = "needs_accommodation")
    private Boolean needsAccommodation = false;

    @Column(name = "needs_transport")
    private Boolean needsTransport = false;

    @Column(name = "rating_before_tournament")
    private Integer ratingBeforeTournament;

    @Column(name = "rating_after_tournament")
    private Integer ratingAfterTournament;

    @Column(name = "delta_total")
    private Integer deltaTotal;

    private Integer place;
    private Integer seed;

    @Column(name = "group_name")
    private String groupName;

    // ===== ДОБАВЛЕННЫЕ ПОЛЯ =====
    @Column(name = "points_earned")
    private Integer pointsEarned;

    @Column(name = "games_played")
    private Integer gamesPlayed = 0;

    @Column(name = "wins")
    private Integer wins = 0;

    @Column(name = "losses")
    private Integer losses = 0;
    // ============================

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        registeredAt = LocalDateTime.now();
        if (status == null) {
            status = "PENDING";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
