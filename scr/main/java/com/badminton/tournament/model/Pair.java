package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "pairs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false)
    private User player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false)
    private User player2;

    @Column(name = "pair_type", nullable = false, length = 10)
    private String pairType;  // MD, WD, XD

    @Column(name = "pair_name")
    private String pairName;

    @Column(name = "pair_rating")
    private Integer pairRating = 0;

    @Column(name = "games_played")
    private Integer gamesPlayed = 0;

    @Column(nullable = false)
    private Integer wins = 0;

    @Column(nullable = false)
    private Integer losses = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;

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

    public String getDisplayName() {
        if (pairName != null && !pairName.isEmpty()) {
            return pairName;
        }
        return player1.getFullName() + " / " + player2.getFullName();
    }

    public void addWin() {
        this.wins++;
        this.gamesPlayed++;
    }

    public void addLoss() {
        this.losses++;
        this.gamesPlayed++;
    }
}
