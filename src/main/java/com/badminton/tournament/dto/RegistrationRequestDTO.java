package com.badminton.tournament.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrationRequestDTO {

    @NotNull(message = "ID турнира обязателен")
    private Long tournamentId;

    @NotNull(message = "ID категории обязателен")
    private Integer categoryId;

    // Для парных категорий
    private Long partnerId;

    @Size(max = 100, message = "Название команды не более 100 символов")
    private String teamName;

    @Size(max = 500, message = "Комментарий не более 500 символов")
    private String comment;

    private Boolean needsAccommodation;
    private Boolean needsTransport;

    // Для проверки - нужен ли партнер для этой категории
    // (заполняется на фронтенде, не обязателен)
    private Boolean isDoublesCategory;
}