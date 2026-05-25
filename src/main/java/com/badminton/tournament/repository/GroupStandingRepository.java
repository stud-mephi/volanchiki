package com.badminton.tournament.repository;

import com.badminton.tournament.model.GroupStanding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupStandingRepository extends JpaRepository<GroupStanding, Long> {

    // Поиск по турниру и названию группы
    List<GroupStanding> findByTournamentIdAndGroupName(Long tournamentId, String groupName);

    // Поиск по турниру
    List<GroupStanding> findByTournamentId(Long tournamentId);

    // Рейтинг внутри группы (сортировка по очкам и разнице сетов)
    @Query("SELECT gs FROM GroupStanding gs WHERE gs.tournament.id = :tournamentId AND gs.groupName = :groupName " +
            "ORDER BY gs.points DESC, gs.setsDiff DESC")
    List<GroupStanding> findGroupRankings(@Param("tournamentId") Long tournamentId,
                                          @Param("groupName") String groupName);
}