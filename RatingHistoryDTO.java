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
public class RatingHistoryDTO {

    private Long id;
    private LocalDateTime changeDate;
    private String formattedDate;

    // Категория
    private String categoryType;
    private String categoryGender;
    private String categoryDisplay;

    // Рейтинг
    private Integer oldRating;
    private Integer newRating;
    private Integer changeValue;
    private String changeSign; // "+" или "-"

    // Причина изменения
    private String reason;
    private String reasonShort;

    // Связанный турнир
    private Long tournamentId;
    private String tournamentTitle;

    // Ручная правка
    private Long organizerId;
    private String organizerName;
    private Boolean isManualAdjustment;

    // Для отображения
    private Boolean isIncrease;
    private Boolean isDecrease;
}