package com.badminton.tournament.repository;

import com.badminton.tournament.model.Rating;
import com.badminton.tournament.model.RatingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {
    
    // Поиск по пользователю
    List<Rating> findByUserId(Long userId);
    Optional<Rating> findByUserIdAndCategoryTypeAndCategoryGender(Long userId, String categoryType, String categoryGender);
    
    // Поиск по категории
    List<Rating> findByCategoryTypeAndCategoryGender(String categoryType, String categoryGender);
    Page<Rating> findByCategoryTypeAndCategoryGender(String categoryType, String categoryGender, Pageable pageable);
    
    // Поиск с фильтрацией по минимальному количеству игр
    @Query("SELECT r FROM Rating r WHERE r.categoryType = :categoryType AND r.categoryGender = :categoryGender " +
           "AND r.gamesPlayed >= :minGames ORDER BY r.ratingValue DESC")
    List<Rating> findTopRatings(@Param("categoryType") String categoryType,
                                @Param("categoryGender") String categoryGender,
                                @Param("minGames") Integer minGames,
                                Pageable pageable);
    
    // Поиск по городу
    @Query("SELECT r FROM Rating r JOIN r.user u WHERE u.city = :city AND r.categoryType = :categoryType AND r.categoryGender = :categoryGender")
    List<Rating> findByCity(@Param("city") String city,
                           @Param("categoryType") String categoryType,
                           @Param("categoryGender") String categoryGender);
    
    // Проверка существования
    boolean existsByUserIdAndCategoryTypeAndCategoryGender(Long userId, String categoryType, String categoryGender);
    
    // Статистика
    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.categoryType = :categoryType AND r.categoryGender = :categoryGender AND r.gamesPlayed > 0")
    Double getAverageRating(@Param("categoryType") String categoryType,
                           @Param("categoryGender") String categoryGender);
    
    @Query("SELECT MAX(r.ratingValue) FROM Rating r WHERE r.categoryType = :categoryType AND r.categoryGender = :categoryGender")
    Integer getMaxRating(@Param("categoryType") String categoryType,
                        @Param("categoryGender") String categoryGender);
    
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.categoryType = :categoryType AND r.categoryGender = :categoryGender AND r.gamesPlayed > 0")
    long countActivePlayers(@Param("categoryType") String categoryType,
                           @Param("categoryGender") String categoryGender);
    
    // Обновление рейтинга
    @Modifying
    @Transactional
    @Query("UPDATE Rating r SET r.ratingValue = :newRating, r.lastUpdated = CURRENT_TIMESTAMP " +
           "WHERE r.userId = :userId AND r.categoryType = :categoryType AND r.categoryGender = :categoryGender")
    void updateRating(@Param("userId") Long userId,
                     @Param("categoryType") String categoryType,
                     @Param("categoryGender") String categoryGender,
                     @Param("newRating") Integer newRating);
    
    // Обновление статистики
    @Modifying
    @Transactional
    @Query("UPDATE Rating r SET r.gamesPlayed = r.gamesPlayed + 1, r.wins = r.wins + 1, r.lastUpdated = CURRENT_TIMESTAMP " +
           "WHERE r.userId = :userId AND r.categoryType = :categoryType AND r.categoryGender = :categoryGender")
    void incrementWin(@Param("userId") Long userId,
                     @Param("categoryType") String categoryType,
                     @Param("categoryGender") String categoryGender);
    
    @Modifying
    @Transactional
    @Query("UPDATE Rating r SET r.gamesPlayed = r.gamesPlayed + 1, r.losses = r.losses + 1, r.lastUpdated = CURRENT_TIMESTAMP " +
           "WHERE r.userId = :userId AND r.categoryType = :categoryType AND r.categoryGender = :categoryGender")
    void incrementLoss(@Param("userId") Long userId,
                      @Param("categoryType") String categoryType,
                      @Param("categoryGender") String categoryGender);
}
