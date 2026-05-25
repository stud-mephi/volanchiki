package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "ranking_positions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Integer rank;

    private Integer rating;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "category_gender")
    private String categoryGender;
}
