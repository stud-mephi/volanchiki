package com.badminton.tournament.repository;

import com.badminton.tournament.model.RatingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RatingHistoryRepository extends JpaRepository<RatingHistory, Long> {
    
    // Поиск по пользователю
    List<RatingHistory> findByUserId(Long userId);
    Page<RatingHistory> findByUserId(Long userId, Pageable pageable);
    
    // Поиск по пользователю и категории
    List<RatingHistory> findByUserIdAndCategoryTypeAndCategoryGender(Long userId, String categoryType, String categoryGender);
    
    // Поиск по турниру
    List<RatingHistory> findByTournamentId(Long tournamentId);
    
    // Поиск по дате
    List<RatingHistory> findByChangeDateBetween(LocalDateTime start, LocalDateTime end);
    
    // Поиск изменений за период
    @Query("SELECT rh FROM RatingHistory rh WHERE rh.userId = :userId " +
           "AND rh.changeDate BETWEEN :startDate AND :endDate ORDER BY rh.changeDate DESC")
    List<RatingHistory> findUserHistoryForPeriod(@Param("userId") Long userId,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);
    
    // Топ изменений рейтинга
    @Query("SELECT rh FROM RatingHistory rh WHERE rh.categoryType = :categoryType " +
           "AND rh.categoryGender = :categoryGender ORDER BY rh.changeValue DESC")
    List<RatingHistory> findTopChanges(@Param("categoryType") String categoryType,
                                       @Param("categoryGender") String categoryGender,
                                       Pageable pageable);
    
    // Статистика
    @Query("SELECT AVG(CAST(rh.changeValue AS double)) FROM RatingHistory rh WHERE rh.userId = :userId")
    Double getAverageChange(@Param("userId") Long userId);
    
    @Query("SELECT SUM(rh.changeValue) FROM RatingHistory rh WHERE rh.userId = :userId " +
           "AND rh.categoryType = :categoryType AND rh.categoryGender = :categoryGender")
    Integer getTotalChange(@Param("userId") Long userId,
                          @Param("categoryType") String categoryType,
                          @Param("categoryGender") String categoryGender);
    
    // Подсчет
    long countByUserId(Long userId);
    long countByTournamentId(Long tournamentId);
}
