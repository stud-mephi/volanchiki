package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    private Integer id;
    
    @Column(unique = true, nullable = false, length = 10)
    private String code; // WS, MS, WD, MD, XD
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String type; // SINGLES, DOUBLES, MIXED
    
    private String gender; // MALE, FEMALE, MIXED
    
    public boolean isDoubles() {
        return "DOUBLES".equals(type) || "MIXED".equals(type);
    }
}
