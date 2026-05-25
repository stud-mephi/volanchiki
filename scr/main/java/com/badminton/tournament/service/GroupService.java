package com.badminton.tournament.service;

import com.badminton.tournament.dto.GroupStandingDTO;
import com.badminton.tournament.exception.ResourceNotFoundException;
import com.badminton.tournament.model.Group;
import com.badminton.tournament.model.GroupStanding;
import com.badminton.tournament.model.Registration;
import com.badminton.tournament.repository.GroupRepository;
import com.badminton.tournament.repository.GroupStandingRepository;
import com.badminton.tournament.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupStandingRepository groupStandingRepository;
    private final RegistrationRepository registrationRepository;

    public List<GroupStandingDTO> getStandings(Long tournamentId, Integer categoryId, String groupCode) {
        Group group = groupRepository.findByTournamentIdAndCategoryIdAndCode(tournamentId, categoryId, groupCode);
        if (group == null) throw new ResourceNotFoundException("Группа не найдена");
        return groupStandingRepository.findByTournamentIdAndGroupName(tournamentId, groupCode).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void addToGroup(Long registrationId, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Группа не найдена"));
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Регистрация не найдена"));

        GroupStanding standing = new GroupStanding();
        standing.setTournament(group.getTournament());
        standing.setGroupName(group.getCode());
        standing.setRegistration(registration);
        standing.setMatchesPlayed(0);
        standing.setMatchesWon(0);
        standing.setMatchesLost(0);
        standing.setPoints(0);
        standing.setSetsDiff(0);
        groupStandingRepository.save(standing);
        groupRepository.incrementTeamsCount(groupId);
    }

    private GroupStandingDTO toDTO(GroupStanding gs) {
        return GroupStandingDTO.builder()
                .id(gs.getId())
                .groupId(gs.getTournament().getId())
                .groupCode(gs.getGroupName())
                .registrationId(gs.getRegistration().getId())
                .playerName(gs.getRegistration().getUser().getFullName())
                .matchesPlayed(gs.getMatchesPlayed())
                .matchesWon(gs.getMatchesWon())
                .matchesLost(gs.getMatchesLost())
                .setsWon(0)
                .setsLost(0)
                .pointsScored(0)
                .pointsConceded(0)
                .tournamentPoints(gs.getPoints())
                .groupPlace(0)
                .setDifference(gs.getSetsDiff())
                .pointDifference(0)
                .build();
    }
}