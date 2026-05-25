package com.badminton.tournament.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MatchResultDTO {

    @NotNull(message = "ID матча обязателен")
    private Long matchId;

    @NotNull(message = "ID победителя обязателен")
    private Long winnerRegistrationId;

    @NotBlank(message = "Счет обязателен")
    @Pattern(regexp = "^(\\d{1,2}-\\d{1,2})(,\\s*\\d{1,2}-\\d{1,2})*$",
            message = "Некорректный формат счета. Пример: 21-15, 21-18")
    private String score;

    // Опционально: можно добавить детали матча
    private Integer durationMinutes; // длительность матча в минутах
    private String matchNotes; // заметки к матчу
}