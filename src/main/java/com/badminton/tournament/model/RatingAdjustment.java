package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "rating_adjustments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer oldRating;

    @Column(nullable = false)
    private Integer newRating;

    @Column(nullable = false)
    private Integer changeValue;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @Column(name = "is_approved")
    private Boolean isApproved = false;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
