package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RatingId.class)
public class Rating {
    
    @Id
    @Column(name = "user_id")
    private Long userId;
    
    @Id
    @Column(name = "category_type", length = 20)
    private String categoryType;  // SINGLES, DOUBLES
    
    @Id
    @Column(name = "category_gender", length = 10)
    private String categoryGender;  // MALE, FEMALE, MIXED
    
    @Column(name = "rating_value", nullable = false)
    private Integer ratingValue = 0;
    
    @Column(name = "games_played", nullable = false)
    private Integer gamesPlayed = 0;
    
    @Column(nullable = false)
    private Integer wins = 0;
    
    @Column(nullable = false)
    private Integer losses = 0;
    
    @Column(name = "win_rate", precision = 5, scale = 2)
    private BigDecimal winRate;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
        calculateWinRate();
    }
    
    public void calculateWinRate() {
        if (gamesPlayed != null && gamesPlayed > 0 && wins != null) {
            this.winRate = BigDecimal.valueOf((double) wins / gamesPlayed * 100)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            this.winRate = null;
        }
    }
    
    public void addWin() {
        this.wins = (this.wins != null ? this.wins : 0) + 1;
        this.gamesPlayed = (this.gamesPlayed != null ? this.gamesPlayed : 0) + 1;
        calculateWinRate();
    }
    
    public void addLoss() {
        this.losses = (this.losses != null ? this.losses : 0) + 1;
        this.gamesPlayed = (this.gamesPlayed != null ? this.gamesPlayed : 0) + 1;
        calculateWinRate();
    }
    
    public void updateRating(int newRating) {
        this.ratingValue = newRating;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public String getCategoryDisplay() {
        String typeDisplay = "SINGLES".equals(categoryType) ? "Одиночный" : "Парный";
        String genderDisplay = "";
        if ("MALE".equals(categoryGender)) genderDisplay = "Мужской";
        else if ("FEMALE".equals(categoryGender)) genderDisplay = "Женский";
        else if ("MIXED".equals(categoryGender)) genderDisplay = "Микст";
        return genderDisplay + " " + typeDisplay;
    }
}
