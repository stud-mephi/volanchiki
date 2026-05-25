package com.badminton.tournament.repository;

import com.badminton.tournament.model.Pair;
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
public interface PairRepository extends JpaRepository<Pair, Long> {
    
    // Поиск по игрокам
    List<Pair> findByPlayer1IdOrPlayer2Id(Long player1Id, Long player2Id);
    
    @Query("SELECT p FROM Pair p WHERE p.player1.id = :userId OR p.player2.id = :userId")
    List<Pair> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Pair p WHERE (p.player1.id = :player1Id AND p.player2.id = :player2Id) " +
           "OR (p.player1.id = :player2Id AND p.player2.id = :player1Id)")
    Optional<Pair> findByPlayers(@Param("player1Id") Long player1Id, @Param("player2Id") Long player2Id);
    
    // Поиск по типу пары
    List<Pair> findByPairType(String pairType);
    Page<Pair> findByPairType(String pairType, Pageable pageable);
    
    // Поиск активных пар
    @Query("SELECT p FROM Pair p WHERE p.isActive = true")
    List<Pair> findActivePairs();
    
    // Поиск по названию
    @Query("SELECT p FROM Pair p WHERE LOWER(p.pairName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Pair> searchByName(@Param("name") String name);
    
    // Проверка существования
    boolean existsByPlayer1IdAndPlayer2IdAndPairType(Long player1Id, Long player2Id, String pairType);
    
    // Топ пар по рейтингу
    @Query("SELECT p FROM Pair p WHERE p.pairType = :pairType AND p.gamesPlayed >= :minGames " +
           "ORDER BY p.pairRating DESC")
    List<Pair> findTopPairs(@Param("pairType") String pairType, @Param("minGames") Integer minGames);
    
    // Обновление статистики
    @Modifying
    @Transactional
    @Query("UPDATE Pair p SET p.pairRating = :newRating WHERE p.id = :id")
    void updateRating(@Param("id") Long id, @Param("newRating") Integer newRating);
    
    @Modifying
    @Transactional
    @Query("UPDATE Pair p SET p.wins = p.wins + 1, p.gamesPlayed = p.gamesPlayed + 1 WHERE p.id = :id")
    void incrementWin(@Param("id") Long id);
    
    @Modifying
    @Transactional
    @Query("UPDATE Pair p SET p.losses = p.losses + 1, p.gamesPlayed = p.gamesPlayed + 1 WHERE p.id = :id")
    void incrementLoss(@Param("id") Long id);
    
    // Статистика
    @Query("SELECT AVG(CAST(p.pairRating AS double)) FROM Pair p WHERE p.pairType = :pairType AND p.gamesPlayed > 0")
    Double getAverageRating(@Param("pairType") String pairType);
    
    long countByPairType(String pairType);
}
