package com.badminton.tournament.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDTO {

    private Long id;
    private String status;
    private String statusDescription;

    // Участник
    private Long userId;
    private String userFullName;
    private String userNickname;
    private String userCity;
    private Integer userAge;

    // Турнир
    private Long tournamentId;
    private String tournamentTitle;
    private LocalDate tournamentStartDate;
    private LocalDate tournamentEndDate;
    private String tournamentCity;
    private String tournamentStatus;

    // Категория
    private Integer categoryId;
    private String categoryCode;
    private String categoryName;
    private String categoryType;

    // Партнер (для парных категорий)
    private Long partnerId;
    private String partnerName;
    private String partnerNickname;
    private String teamName;

    // Даты
    private LocalDateTime registeredAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime cancelledAt;

    // Дополнительно
    private String comment;
    private Boolean needsAccommodation;
    private Boolean needsTransport;

    // Рейтинг на момент регистрации
    private Integer ratingBeforeTournament;
    private Integer ratingAfterTournament;
    private Integer deltaTotal;

    // Результаты
    private Integer place;
    private Integer seed;
    private String groupName;

    // Флаги
    private boolean canCancel; // можно ли отменить регистрацию
    private boolean canConfirm; // может ли организатор подтвердить
}