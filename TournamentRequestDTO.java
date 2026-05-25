package com.badminton.tournament.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class TournamentRequestDTO {

    @NotBlank(message = "Название обязательно")
    private String title;

    private String description;

    @NotNull(message = "Дата начала обязательна")
    private LocalDate startDate;

    @NotNull(message = "Дата окончания обязательна")
    private LocalDate endDate;

    @NotBlank(message = "Город обязателен")
    private String city;

    private String  venue;
    private String  address;
    private Integer minAge;
    private Integer maxAge;
    private Integer maxParticipants;
    private LocalDate registrationDeadline;

    @NotEmpty(message = "Выберите хотя бы одну категорию")
    private List<Integer> categoryIds;  // [1, 2, 3]

    private List<String> groupCodes;    // ["A", "B", "C"]
}
