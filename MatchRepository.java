package com.badminton.tournament.repository;

import com.badminton.tournament.model.Registration;
import com.badminton.tournament.model.Match;
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
public interface MatchRepository extends JpaRepository<Match, Long> {
    
    // Поиск по турниру
    List<Match> findByTournamentId(Long tournamentId);
    Page<Match> findByTournamentId(Long tournamentId, Pageable pageable);
    List<Match> findByTournamentIdOrderByMatchNumberAsc(Long tournamentId);
    
    // Поиск по раунду
    List<Match> findByTournamentIdAndMatchRound(Long tournamentId, String matchRound);
    
    // Необработанные матчи
    List<Match> findByTournamentIdAndProcessedFalse(Long tournamentId);
    List<Match> findByProcessedFalse();
    
    // Поиск по регистрациям
    List<Match> findByRegistration1IdOrRegistration2Id(Long registrationId1, Long registrationId2);
    List<Match> findByWinnerId(Long winnerId);
    
    // Проверка существования
    boolean existsByTournamentIdAndMatchNumber(Long tournamentId, Integer matchNumber);
    Optional<Match> findByTournamentIdAndMatchNumber(Long tournamentId, Integer matchNumber);
    
    // Поиск следующего матча
    @Query("SELECT m FROM Match m WHERE m.tournament.id = :tournamentId " +
           "AND m.matchNumber > :currentMatchNumber ORDER BY m.matchNumber ASC")
    List<Match> findNextMatches(@Param("tournamentId") Long tournamentId, 
                                @Param("currentMatchNumber") Integer currentMatchNumber);
    
    // Статистика
    @Query("SELECT COUNT(m) FROM Match m WHERE m.tournament.id = :tournamentId AND m.processed = true")
    long countProcessedByTournament(@Param("tournamentId") Long tournamentId);
    
    @Query("SELECT COUNT(m) FROM Match m WHERE m.tournament.id = :tournamentId")
    long countByTournamentId(@Param("tournamentId") Long tournamentId);
    
    // Поиск по игроку
    @Query("SELECT m FROM Match m WHERE m.registration1.user.id = :userId OR m.registration2.user.id = :userId")
    List<Match> findByUserId(@Param("userId") Long userId);
    
    // Обновление
    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.processed = true, m.winner = :winner, m.score = :score, m.delta = :delta WHERE m.id = :id")
    void markProcessed(@Param("id") Long id, 
                       @Param("winner") Registration winner, 
                       @Param("score") String score, 
                       @Param("delta") Integer delta);
    
    // Удаление
    @Modifying
    @Transactional
    @Query("DELETE FROM Match m WHERE m.tournament.id = :tournamentId")
    void deleteByTournamentId(@Param("tournamentId") Long tournamentId);
}
