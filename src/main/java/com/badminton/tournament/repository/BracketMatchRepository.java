package com.badminton.tournament.repository;

import com.badminton.tournament.model.BracketMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BracketMatchRepository extends JpaRepository<BracketMatch, Long> {
    
    // Поиск по турниру
    List<BracketMatch> findByTournamentId(Long tournamentId);
    
    // Поиск по турниру и категории
    List<BracketMatch> findByTournamentIdAndCategoryId(Long tournamentId, Integer categoryId);
    
    // Поиск по раунду
    List<BracketMatch> findByTournamentIdAndCategoryIdAndRoundNumber(Long tournamentId, Integer categoryId, Integer roundNumber);
    List<BracketMatch> findByTournamentIdAndCategoryIdAndRoundName(Long tournamentId, Integer categoryId, String roundName);
    
    // Поиск финала
    @Query("SELECT bm FROM BracketMatch bm WHERE bm.tournament.id = :tournamentId " +
           "AND bm.category.id = :categoryId AND bm.roundName = 'FINAL'")
    Optional<BracketMatch> findFinal(@Param("tournamentId") Long tournamentId, @Param("categoryId") Integer categoryId);
    
    // Поиск по регистрации игрока
    @Query("SELECT bm FROM BracketMatch bm WHERE bm.player1Registration.id = :registrationId " +
           "OR bm.player2Registration.id = :registrationId")
    List<BracketMatch> findByRegistrationId(@Param("registrationId") Long registrationId);
    
    // Поиск матчей без победителя
    @Query("SELECT bm FROM BracketMatch bm WHERE bm.tournament.id = :tournamentId " +
           "AND bm.status != 'COMPLETED' AND bm.player1Registration IS NOT NULL AND bm.player2Registration IS NOT NULL")
    List<BracketMatch> findPendingMatches(@Param("tournamentId") Long tournamentId);
    
    // Поиск следующего матча (по next_match_id)
    Optional<BracketMatch> findByNextMatch_Id(Long nextMatchId);
    
    // Обновление статуса
    @Modifying
    @Transactional
    @Query("UPDATE BracketMatch bm SET bm.status = :status WHERE bm.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") String status);
    
    // Обновление результата
    @Modifying
    @Transactional
    @Query("UPDATE BracketMatch bm SET bm.winnerRegistration.id = :winnerId, bm.score = :score, " +
           "bm.status = 'COMPLETED' WHERE bm.id = :id")
    void updateResult(@Param("id") Long id, @Param("winnerId") Long winnerId, @Param("score") String score);
    
    // Установка игроков в следующий раунд
    @Modifying
    @Transactional
    @Query("UPDATE BracketMatch bm SET bm.player1Registration.id = :winnerId WHERE bm.id = :id")
    void setPlayer1(@Param("id") Long id, @Param("winnerId") Long winnerId);
    
    @Modifying
    @Transactional
    @Query("UPDATE BracketMatch bm SET bm.player2Registration.id = :winnerId WHERE bm.id = :id")
    void setPlayer2(@Param("id") Long id, @Param("winnerId") Long winnerId);
    
    // Подсчет
    long countByTournamentIdAndCategoryId(Long tournamentId, Integer categoryId);
    long countByTournamentIdAndCategoryIdAndStatus(Long tournamentId, Integer categoryId, String status);
    
    // Статистика
    @Query("SELECT bm.roundName, COUNT(bm) FROM BracketMatch bm " +
           "WHERE bm.tournament.id = :tournamentId AND bm.category.id = :categoryId " +
           "GROUP BY bm.roundName ORDER BY bm.roundNumber")
    List<Object[]> countByRound(@Param("tournamentId") Long tournamentId, @Param("categoryId") Integer categoryId);
}
