package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "group_standings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupStanding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    // Временно отключаем связь с Group из-за проблемы с составным ключом
    // @ManyToOne
    // @JoinColumn(name = "group_id")
    // private Group group;

    @ManyToOne
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @Column(name = "matches_played")
    private Integer matchesPlayed = 0;

    @Column(name = "matches_won")
    private Integer matchesWon = 0;

    @Column(name = "matches_lost")
    private Integer matchesLost = 0;

    private Integer points = 0;

    @Column(name = "sets_diff")
    private Integer setsDiff = 0;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    @PrePersist
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}