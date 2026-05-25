package com.badminton.tournament.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BracketMatchDTO {
    private Long    id;
    private Long    tournamentId;
    private Integer categoryId;
    private String  categoryCode;
    private String  roundName;
    private Integer roundNumber;
    private Integer matchOrder;
    private String  player1Name;
    private String  player2Name;
    private String  winnerName;
    private String  score;
    private String  status;
    private Long    nextMatchId;
}
