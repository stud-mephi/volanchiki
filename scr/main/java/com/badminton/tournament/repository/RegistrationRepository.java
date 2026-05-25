package com.badminton.tournament.repository;

import com.badminton.tournament.model.Registration;
import com.badminton.tournament.model.User;
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
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    
    // Поиск по пользователю
    List<Registration> findByUserId(Long userId);
    Page<Registration> findByUserId(Long userId, Pageable pageable);
    List<Registration> findByUserIdAndStatus(Long userId, String status);
    
    // Поиск по турниру
    List<Registration> findByTournamentId(Long tournamentId);
    List<Registration> findByTournamentIdAndStatus(Long tournamentId, String status);
    Page<Registration> findByTournamentId(Long tournamentId, Pageable pageable);
    
    // Поиск по категории
    List<Registration> findByTournamentIdAndCategoryId(Long tournamentId, Integer categoryId);
    List<Registration> findByTournamentIdAndCategoryIdAndStatus(Long tournamentId, Integer categoryId, String status);
    
    // Проверка существования
    boolean existsByUserIdAndTournamentIdAndCategoryId(Long userId, Long tournamentId, Integer categoryId);
    
    // Поиск по партнеру
    List<Registration> findByPartnerId(Long partnerId);
    List<Registration> findByTournamentIdAndPartnerId(Long tournamentId, Long partnerId);
    
    // Поиск по паре

    
    // Поиск по турниру, категории и пользователю
    @Query("SELECT r FROM Registration r WHERE r.tournament.id = :tournamentId " +
           "AND r.category.id = :categoryId AND r.user.id = :userId")
    Optional<Registration> findByTournamentIdAndCategoryIdAndUserId(
            @Param("tournamentId") Long tournamentId,
            @Param("categoryId") Integer categoryId,
            @Param("userId") Long userId);
    
    // Подсчет участников
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.tournament.id = :tournamentId AND r.status = 'CONFIRMED'")
    int countConfirmedByTournament(@Param("tournamentId") Long tournamentId);
    
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.tournament.id = :tournamentId " +
           "AND r.category.id = :categoryId AND r.status = 'CONFIRMED'")
    int countConfirmedByTournamentAndCategory(@Param("tournamentId") Long tournamentId, 
                                               @Param("categoryId") Integer categoryId);
    
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.tournament.id = :tournamentId AND r.status = 'WAITLISTED'")
    int countWaitlistedByTournament(@Param("tournamentId") Long tournamentId);
    
    // Поиск участников с результатами
    @Query("SELECT r FROM Registration r WHERE r.tournament.id = :tournamentId AND r.place IS NOT NULL ORDER BY r.place")
    List<Registration> findWithResultsByTournament(@Param("tournamentId") Long tournamentId);
    
    // Поиск по месту
    List<Registration> findByTournamentIdAndPlaceBetween(Long tournamentId, Integer minPlace, Integer maxPlace);
    
    // Поиск участников, нуждающихся в проживании/трансфере
    @Query("SELECT r FROM Registration r WHERE r.tournament.id = :tournamentId AND r.needsAccommodation = true")
    List<Registration> findWithAccommodation(@Param("tournamentId") Long tournamentId);
    
    @Query("SELECT r FROM Registration r WHERE r.tournament.id = :tournamentId AND r.needsTransport = true")
    List<Registration> findWithTransport(@Param("tournamentId") Long tournamentId);
    
    // Обновление статуса
    @Modifying
    @Transactional
    @Query("UPDATE Registration r SET r.status = :status WHERE r.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") String status);
    
    @Modifying
    @Transactional
    @Query("UPDATE Registration r SET r.status = :status, r.confirmedAt = CURRENT_TIMESTAMP WHERE r.id = :id")
    void confirmRegistration(@Param("id") Long id, @Param("status") String status);
    
    // Обновление результатов
    @Modifying
    @Transactional
    @Query("UPDATE Registration r SET r.place = :place, r.deltaTotal = :points WHERE r.id = :id")
    void updateResults(@Param("id") Long id, @Param("place") Integer place, @Param("points") Integer points);
    
    // Статистика по категориям
    @Query("SELECT r.category.id, COUNT(r) FROM Registration r " +
           "WHERE r.tournament.id = :tournamentId AND r.status = 'CONFIRMED' GROUP BY r.category.id")
    List<Object[]> countByCategory(@Param("tournamentId") Long tournamentId);
    
    // Удаление
    @Modifying
    @Transactional
    @Query("DELETE FROM Registration r WHERE r.tournament.id = :tournamentId")
    void deleteByTournamentId(@Param("tournamentId") Long tournamentId);
}
