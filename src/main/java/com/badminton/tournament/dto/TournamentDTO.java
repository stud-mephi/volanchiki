package com.badminton.tournament.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDTO {

private Long id;
private String title;
private String description;
private String status;
private String statusDescription;

// Организатор
private Long organizerId;
private String organizerName;
private String organizerCity;

// Даты
private LocalDate startDate;
private LocalDate endDate;
private LocalDate registrationDeadline;

// Место
private String city;
private String venue;
private String address;

// Ограничения
private Integer minAge;
private Integer maxAge;
private String ageRange;
private Integer maxParticipants;
private Integer currentParticipants;
private Integer availableSlots;

// Категории и группы
private List<String> categories;
private List<String> categoryNames;
private List<String> groups;

// Статусы
private boolean registrationOpen;
private boolean isCompleted;
private boolean isCancelled;

// Статистика
private Integer registrationsCount;
private Integer confirmedCount;
private Integer waitlistCount;

private LocalDateTime createdAt;
private LocalDateTime updatedAt;
}
