package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;
    
    @ManyToOne
    @JoinColumn(name = "registration_id_1", nullable = false)
    private Registration registration1;
    
    @ManyToOne
    @JoinColumn(name = "registration_id_2", nullable = false)
    private Registration registration2;
    
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Registration winner;
    
    @Column(length = 50)
    private String score;  // "21-15, 21-18"
    
    @Column(name = "rating_1_before", nullable = false)
    private Integer rating1Before;
    
    @Column(name = "rating_2_before", nullable = false)
    private Integer rating2Before;
    
    private Integer delta;  // изменение рейтинга
    
    @Column(name = "match_round")
    private String matchRound;  // 1/16, 1/8, 1/4, 1/2, FINAL
    
    @Column(name = "match_number")
    private Integer matchNumber;
    
    @Column(name = "match_date")
    private LocalDateTime matchDate;
    
    private Boolean processed = false;
    
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    
    @Column(name = "match_notes", columnDefinition = "TEXT")
    private String matchNotes;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (matchDate == null) {
            matchDate = LocalDateTime.now();
        }
        if (processed == null) {
            processed = false;
        }
    }
    
    public boolean isProcessed() {
        return processed != null && processed;
    }
    
    public String getWinnerName() {
        if (winner != null && winner.getUser() != null) {
            return winner.getUser().getFullName();
        }
        return null;
    }
    
    public String getPlayer1Name() {
        if (registration1 != null && registration1.getUser() != null) {
            return registration1.getUser().getFullName();
        }
        return null;
    }
    
    public String getPlayer2Name() {
        if (registration2 != null && registration2.getUser() != null) {
            return registration2.getUser().getFullName();
        }
        return null;
    }
}
