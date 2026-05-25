package com.badminton.tournament.repository;

import com.badminton.tournament.model.Tournament;
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
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    
    // Поиск по уникальным полям
    boolean existsByTitle(String title);
    Optional<Tournament> findByTitle(String title);
    
    // Поиск по организатору
    List<Tournament> findByOrganizerId(Long organizerId);
    Page<Tournament> findByOrganizerId(Long organizerId, Pageable pageable);
    
    // Поиск по статусу
    List<Tournament> findByStatus(String status);
    List<Tournament> findByStatusIn(List<String> statuses);
    Page<Tournament> findByStatus(String status, Pageable pageable);
    
    // Поиск по датам
    List<Tournament> findByStartDate(LocalDate startDate);
    List<Tournament> findByStartDateBetween(LocalDate start, LocalDate end);
    
    @Query("SELECT t FROM Tournament t WHERE t.startDate >= :date")
    List<Tournament> findUpcoming(@Param("date") LocalDate date);
    
    @Query("SELECT t FROM Tournament t WHERE t.startDate <= :date AND t.endDate >= :date")
    List<Tournament> findActiveOnDate(@Param("date") LocalDate date);
    
    // Поиск с фильтрами
    @Query("SELECT DISTINCT t FROM Tournament t " +
           "LEFT JOIN t.categories c " +
           "WHERE (:city IS NULL OR t.city = :city) " +
           "AND (:status IS NULL OR t.status = :status) " +
           "AND (:fromDate IS NULL OR t.startDate >= :fromDate) " +
           "AND (:toDate IS NULL OR t.endDate <= :toDate) " +
           "AND (:categoryId IS NULL OR c.id = :categoryId) " +
           "ORDER BY t.startDate")
    List<Tournament> findByFilters(@Param("city") String city,
                                   @Param("status") String status,
                                   @Param("fromDate") LocalDate fromDate,
                                   @Param("toDate") LocalDate toDate,
                                   @Param("categoryId") Integer categoryId);
    
    // Поиск по категории
    @Query("SELECT DISTINCT t FROM Tournament t JOIN t.categories c WHERE c.code = :categoryCode ORDER BY t.startDate")
    List<Tournament> findByCategory(@Param("categoryCode") String categoryCode);
    
    // Поиск активных турниров
    @Query("SELECT t FROM Tournament t WHERE t.status IN ('REGISTRATION_OPEN', 'IN_PROGRESS')")
    List<Tournament> findActiveTournaments();
    
    @Query("SELECT t FROM Tournament t WHERE t.status = 'REGISTRATION_OPEN' AND t.registrationDeadline >= CURRENT_DATE")
    List<Tournament> findOpenForRegistration();
    
    // Поиск по городу
    List<Tournament> findByCity(String city);
    List<Tournament> findByCityContainingIgnoreCase(String city);
    
    // Активные турниры организатора
    @Query("SELECT t FROM Tournament t WHERE t.organizer.id = :organizerId AND t.status IN ('REGISTRATION_OPEN', 'IN_PROGRESS')")
    List<Tournament> findActiveByOrganizer(@Param("organizerId") Long organizerId);
    
    // Статистика
    long countByStatus(String status);
    long countByOrganizerId(Long organizerId);
    long countByStatusIn(List<String> statuses);
    
    @Query("SELECT COUNT(t) FROM Tournament t WHERE t.startDate >= CURRENT_DATE")
    long countUpcoming();
    
    @Query("SELECT t.city, COUNT(t) FROM Tournament t GROUP BY t.city ORDER BY COUNT(t) DESC")
    List<Object[]> countByCity();
    
    @Query("SELECT EXTRACT(YEAR FROM t.startDate) as year, COUNT(t) FROM Tournament t GROUP BY year ORDER BY year DESC")
    List<Object[]> countByYear();
    
    // Проверка пересечения дат
    @Query("SELECT t FROM Tournament t WHERE t.organizer.id = :organizerId " +
           "AND t.status != 'CANCELLED' " +
           "AND ((t.startDate BETWEEN :startDate AND :endDate) " +
           "OR (t.endDate BETWEEN :startDate AND :endDate) " +
           "OR (t.startDate <= :startDate AND t.endDate >= :endDate))")
    List<Tournament> findOverlappingTournaments(@Param("organizerId") Long organizerId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
    
    // Обновление статуса
    @Modifying
    @Transactional
    @Query("UPDATE Tournament t SET t.status = :status WHERE t.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") String status);
}
