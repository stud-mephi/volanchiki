package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "rating_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "category_type", nullable = false, length = 20)
    private String categoryType;  // SINGLES, DOUBLES
    
    @Column(name = "category_gender", nullable = false, length = 10)
    private String categoryGender;  // MALE, FEMALE, MIXED
    
    @Column(name = "old_rating", nullable = false)
    private Integer oldRating;
    
    @Column(name = "new_rating", nullable = false)
    private Integer newRating;
    
    @Column(name = "change_value", nullable = false)
    private Integer changeValue;
    
    @Column(name = "change_date")
    private LocalDateTime changeDate;
    
    @Column(name = "tournament_id")
    private Long tournamentId;
    
    @Column(name = "organizer_id")
    private Long organizerId;
    
    @Column(columnDefinition = "TEXT")
    private String reason;
    
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "tournament_id", insertable = false, updatable = false)
    private Tournament tournament;
    
    @PrePersist
    protected void onCreate() {
        if (changeDate == null) {
            changeDate = LocalDateTime.now();
        }
    }
    
    public String getChangeSign() {
        if (changeValue == null) return "";
        return changeValue > 0 ? "+" : "";
    }
    
    public String getFormattedChangeDate() {
        if (changeDate == null) return "";
        return changeDate.toLocalDate().toString();
    }
    
    public boolean isIncrease() {
        return changeValue != null && changeValue > 0;
    }
    
    public boolean isDecrease() {
        return changeValue != null && changeValue < 0;
    }
}
