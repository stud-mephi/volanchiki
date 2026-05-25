package com.badminton.tournament.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingId implements Serializable {
    
    private Long userId;
    private String categoryType;
    private String categoryGender;
}
