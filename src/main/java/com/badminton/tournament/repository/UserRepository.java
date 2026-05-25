package com.badminton.tournament.repository;

import com.badminton.tournament.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Поиск по уникальным полям
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    
    // Проверка существования
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    
    // Поиск с фильтрацией
    @Query("SELECT u FROM User u WHERE " +
           "(:city IS NULL OR u.city = :city) AND " +
           "(:gender IS NULL OR u.gender = :gender) AND " +
           "(:minAge IS NULL OR u.birthDate <= :maxBirthDate) AND " +
           "(:maxAge IS NULL OR u.birthDate >= :minBirthDate) AND " +
           "(:isActive IS NULL OR u.isActive = :isActive)")
    Page<User> findByFilters(@Param("city") String city,
                             @Param("gender") String gender,
                             @Param("minAge") Integer minAge,
                             @Param("maxAge") Integer maxAge,
                             @Param("maxBirthDate") LocalDate maxBirthDate,
                             @Param("minBirthDate") LocalDate minBirthDate,
                             @Param("isActive") Boolean isActive,
                             Pageable pageable);
    
    // Поиск по имени или никнейму
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.nickname) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.city) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> search(@Param("query") String query);
    
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.nickname) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<User> searchPageable(@Param("query") String query, Pageable pageable);
    
    // Поиск новичков
    @Query("SELECT u FROM User u WHERE u.tournamentsPlayed <= 5 AND u.isActive = true")
    List<User> findNewbies();
    
    @Query("SELECT u FROM User u WHERE u.tournamentsPlayed <= 5 AND u.isActive = true " +
           "ORDER BY u.tournamentsPlayed ASC")
    Page<User> findNewbiesPageable(Pageable pageable);
    
    // Неактивные пользователи
    @Query("SELECT u FROM User u WHERE u.lastActiveDate < :date AND u.isActive = true")
    List<User> findInactiveUsers(@Param("date") LocalDate date);
    
    // Пользователи, не игравшие более года
    @Query("SELECT u FROM User u WHERE u.lastActiveDate < :date AND u.tournamentsPlayed > 0")
    List<User> findUsersInactiveLong(@Param("date") LocalDate date);
    
    // Статистика
    long countByIsActiveTrue();
    long countByCity(String city);
    long countByGender(String gender);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.tournamentsPlayed > 0")
    long countActivePlayers();
    
    @Query("SELECT AVG(CAST(u.tournamentsPlayed AS double)) FROM User u")
    Double getAverageTournamentsPlayed();
    
    // Обновление
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.lastActiveDate = :date WHERE u.id = :userId")
    void updateLastActiveDate(@Param("userId") Long userId, @Param("date") LocalDate date);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.tournamentsPlayed = u.tournamentsPlayed + 1 WHERE u.id = :userId")
    void incrementTournamentsPlayed(@Param("userId") Long userId);
}
