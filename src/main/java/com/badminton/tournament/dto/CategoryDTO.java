package com.badminton.tournament.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

private Integer id;
private String code; // WS, MS, WD, MD, XD
private String name; // Women's Singles, Men's Singles, etc.
private String type; // SINGLES, DOUBLES, MIXED
private String gender; // MALE, FEMALE, MIXED
private String displayName;

// Для парных категорий
private boolean isDoubles;

// Статистика
private Integer participantsCount;
private Integer matchesCount;
}
