package com.badminton.tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private Long   userId;
    private String token;
    private String fullName;
    private String nickname;
    private String role;  // PLAYER, ORGANIZER, ADMIN
}
