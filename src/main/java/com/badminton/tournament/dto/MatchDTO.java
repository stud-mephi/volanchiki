package com.badminton.tournament.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {

private Long id;
private Long tournamentId;
private String tournamentTitle;
private String matchRound;
private Integer matchNumber;

// Игрок 1
private Long registrationId1;
private Long userId1;
private String player1Name;
private String player1Nickname;
private Integer player1RatingBefore;
private Integer player1RatingAfter;

// Игрок 2
private Long registrationId2;
private Long userId2;
private String player2Name;
private String player2Nickname;
private Integer player2RatingBefore;
private Integer player2RatingAfter;

// Результат
private Long winnerId;
private String winnerName;
private String score;
private Integer delta;

// Статус
private Boolean processed;
private LocalDateTime matchDate;
private LocalDateTime createdAt;
}
