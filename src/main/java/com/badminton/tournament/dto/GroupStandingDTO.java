package com.badminton.tournament.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupStandingDTO {
    private Long id;
    private Long groupId;
    private String groupCode;
    private Long registrationId;
    private String playerName;
    private Integer matchesPlayed;
    private Integer matchesWon;
    private Integer matchesLost;
    private Integer setsWon;
    private Integer setsLost;
    private Integer pointsScored;
    private Integer pointsConceded;
    private Integer tournamentPoints;
    private Integer groupPlace;
    private Integer setDifference;
    private Integer pointDifference;
}