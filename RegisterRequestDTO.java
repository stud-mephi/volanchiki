package com.badminton.tournament.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "ФИО обязательно")
    private String fullName;

    @NotBlank(message = "Никнейм обязателен")
    @Size(min = 3, max = 50, message = "Никнейм от 3 до 50 символов")
    private String nickname;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль минимум 6 символов")
    private String password;

    @NotNull(message = "Дата рождения обязательна")
    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate birthDate;

    @NotBlank(message = "Пол обязателен")
    @Pattern(regexp = "MALE|FEMALE", message = "Пол: MALE или FEMALE")
    private String gender;

    private String phone;
    private String city;
}
