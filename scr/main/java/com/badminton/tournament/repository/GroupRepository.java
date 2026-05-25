package com.badminton.tournament.repository;

import com.badminton.tournament.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    // Поиск по турниру
    List<Group> findByTournamentId(Long tournamentId);

    // Поиск по турниру и категории
    List<Group> findByTournamentIdAndCategoryId(Long tournamentId, Integer categoryId);

    // Поиск конкретной группы
    Group findByTournamentIdAndCategoryIdAndCode(Long tournamentId, Integer categoryId, String code);

    Optional<Group> findByCode(String code);

    // Поиск групп с свободными местами
    @Query("SELECT g FROM Group g WHERE g.tournament.id = :tournamentId " +
            "AND g.category.id = :categoryId AND g.teamsCount < g.maxTeams")
    List<Group> findGroupsWithFreeSlots(@Param("tournamentId") Long tournamentId,
                                        @Param("categoryId") Integer categoryId);

    // Обновление количества команд
    @Modifying
    @Transactional
    @Query("UPDATE Group g SET g.teamsCount = g.teamsCount + 1 WHERE g.id = :id")
    void incrementTeamsCount(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Group g SET g.teamsCount = g.teamsCount - 1 WHERE g.id = :id")
    void decrementTeamsCount(@Param("id") Long id);

    // Завершение группы
    @Modifying
    @Transactional
    @Query("UPDATE Group g SET g.isCompleted = true WHERE g.id = :id")
    void markCompleted(@Param("id") Long id);

    // Подсчет
    long countByTournamentId(Long tournamentId);
    long countByTournamentIdAndCategoryId(Long tournamentId, Integer categoryId);
}