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
public class RatingDTO {

// Информация об игроке
private Long userId;
private String fullName;
private String nickname;
private String city;
private Integer age;

// Рейтинг
private String categoryType; // SINGLES или DOUBLES
private String categoryGender; // MALE, FEMALE, MIXED
private String categoryDisplay; // отображаемое название (Мужская одиночка)

private Integer ratingValue;
private Integer position; // место в рейтинге

// Статистика
private Integer gamesPlayed;
private Integer wins;
private Integer losses;
private Double winRate;

// Изменения
private Integer lastChange; // последнее изменение рейтинга
private LocalDateTime lastUpdated;

// Для отображения прогресса
private Integer ratingChangeLastMonth;
private Integer ratingChangeLastYear;
private String trend; // UP, DOWN, STABLE
}
