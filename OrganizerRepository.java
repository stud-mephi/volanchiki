package com.badminton.tournament.repository;

import com.badminton.tournament.model.Organizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    
    // Поиск по уникальным полям
    Optional<Organizer> findByEmail(String email);
    Optional<Organizer> findByVerificationToken(String verificationToken);
    Optional<Organizer> findByResetToken(String resetToken);
    
    // Проверка существования
    boolean existsByEmail(String email);
    
    // Поиск по фильтрам
    @Query("SELECT o FROM Organizer o WHERE " +
           "(:city IS NULL OR o.city = :city) AND " +
           "(:verified IS NULL OR o.isVerified = :verified) AND " +
           "(:active IS NULL OR o.isActive = :active)")
    Page<Organizer> findByFilters(@Param("city") String city,
                                  @Param("verified") Boolean verified,
                                  @Param("active") Boolean active,
                                  Pageable pageable);
    
    @Query("SELECT o FROM Organizer o WHERE o.isVerified = :verified")
    List<Organizer> findByIsVerified(@Param("verified") Boolean verified);
    
    @Query("SELECT o FROM Organizer o WHERE o.isActive = true AND o.isVerified = true")
    List<Organizer> findActiveVerified();
    
    // Поиск по названию
    @Query("SELECT o FROM Organizer o WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Organizer> searchByName(@Param("name") String name);
    
    // Статистика
    long countByIsVerifiedTrue();
    long countByIsActiveTrue();
    
    @Query("SELECT COUNT(o) FROM Organizer o WHERE o.isVerified = false")
    long countPendingVerification();
    
    @Query("SELECT o.city, COUNT(o) FROM Organizer o GROUP BY o.city ORDER BY COUNT(o) DESC")
    List<Object[]> countByCity();
    
    // Обновление
    @Modifying
    @Transactional
    @Query("UPDATE Organizer o SET o.lastLogin = :lastLogin WHERE o.id = :id")
    void updateLastLogin(@Param("id") Long id, @Param("lastLogin") LocalDateTime lastLogin);
    
    @Modifying
    @Transactional
    @Query("UPDATE Organizer o SET o.isVerified = true WHERE o.id = :id")
    void verifyOrganizer(@Param("id") Long id);
}
