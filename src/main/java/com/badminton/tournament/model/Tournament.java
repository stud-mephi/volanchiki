package com.badminton.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tournaments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;
    
    @Column(nullable = false)
    private String status; // DRAFT, REGISTRATION_OPEN, REGISTRATION_CLOSED, IN_PROGRESS, COMPLETED, CANCELLED
    
    @Column(columnDefinition = "TEXT")
    private String address;

    private String description;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(nullable = false)
    private String city;
    
    private String venue;
    
    @Column(name = "min_age")
    private Integer minAge;
    
    @Column(name = "max_age")
    private Integer maxAge;
    
    @Column(name = "max_participants")
    private Integer maxParticipants;
    
    @Column(name = "registration_deadline")
    private LocalDate registrationDeadline;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToMany
    @JoinTable(
        name = "tournament_categories",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "tournament_groups",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "DRAFT";
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public boolean isRegistrationOpen() {
        return "REGISTRATION_OPEN".equals(status) && 
               (registrationDeadline == null || !registrationDeadline.isBefore(LocalDate.now()));
    }
    
    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }
}
