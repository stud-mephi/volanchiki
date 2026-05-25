package com.badminton.tournament.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

private Integer id;
private String code; // A, B, C, D, E, F, G
private String name; // Group A, Group B, etc.
private String description;

// Статистика
private Integer participantsCount;
private Integer tournamentsCount;
}
