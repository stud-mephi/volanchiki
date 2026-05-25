package com.badminton.tournament.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PairDTO {
    private Long    id;
    private Long    player1Id;
    private String  player1Name;
    private Long    player2Id;
    private String  player2Name;
    private String  pairType;
    private String  pairName;
    private Integer pairRating;
    private Integer gamesPlayed;
    private Integer wins;
    private Integer losses;
    private Boolean isActive;
}
