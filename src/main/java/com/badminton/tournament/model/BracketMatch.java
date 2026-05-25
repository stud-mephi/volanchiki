package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "bracket_matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BracketMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Простая связь с группой (без составного ключа)
    //@ManyToOne
    //@JoinColumn(name = "group_id")
    //private Group group;

    private String roundName;
    private Integer roundNumber;
    private Integer matchOrder;
    private String status;
    private String score;
    private Integer courtNumber;
    private LocalDateTime matchDate;

    @ManyToOne
    @JoinColumn(name = "player1_registration_id")
    private Registration player1Registration;

    @ManyToOne
    @JoinColumn(name = "player2_registration_id")
    private Registration player2Registration;

    @ManyToOne
    @JoinColumn(name = "winner_registration_id")
    private Registration winnerRegistration;

    @ManyToOne
    @JoinColumn(name = "next_match_id")
    private BracketMatch nextMatch;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PreUpdate
    @PrePersist
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getNextMatchId() {
        return nextMatch != null ? nextMatch.getId() : null;
    }
}