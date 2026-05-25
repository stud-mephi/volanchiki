package com.badminton.tournament.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

private Long id;
private String fullName;
private String nickname;
private String email;
private String phone;
private String city;
private String gender;
private Integer age;
private Boolean isActive;
private Boolean isNewbie;
private Integer tournamentsPlayed;
private LocalDate firstTournamentDate;
private LocalDate lastActiveDate;
private LocalDateTime createdAt;

// Одиночный рейтинг
private Integer singlesRating;
private Integer singlesGamesPlayed;
private Integer singlesWins;
private Integer singlesLosses;
private Double singlesWinRate;

// Парный рейтинг
private Integer doublesRating;
private Integer doublesGamesPlayed;
private Integer doublesWins;
private Integer doublesLosses;
private Double doublesWinRate;
}
