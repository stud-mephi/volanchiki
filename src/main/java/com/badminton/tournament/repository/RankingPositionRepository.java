package com.badminton.tournament.repository;

import com.badminton.tournament.model.RankingPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingPositionRepository extends JpaRepository<RankingPosition, Long> {

    // Топ-N игроков по категории (исправлено: position → rank)
    List<RankingPosition> findByCategoryTypeAndCategoryGenderOrderByRankAsc(
            String categoryType, String categoryGender);

    // Позиция конкретного игрока
    Optional<RankingPosition> findByUserIdAndCategoryTypeAndCategoryGender(
            Long userId, String categoryType, String categoryGender);

    // Поиск по диапазону позиций (уже правильно: используется rank)
    @Query("SELECT rp FROM RankingPosition rp WHERE rp.categoryType = :type AND rp.categoryGender = :gender AND rp.rank BETWEEN :from AND :to ORDER BY rp.rank")
    List<RankingPosition> findTopN(@Param("type") String type,
                                   @Param("gender") String gender,
                                   @Param("from") Integer from,
                                   @Param("to") Integer to);

    // Удаление старых расчётов
    void deleteByCategoryTypeAndCategoryGender(String categoryType, String categoryGender);
}