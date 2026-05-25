package com.badminton.tournament.service;

import com.badminton.tournament.dto.TournamentDTO;
import com.badminton.tournament.dto.TournamentRequestDTO;
import com.badminton.tournament.exception.ResourceNotFoundException;
import com.badminton.tournament.model.Category;
import com.badminton.tournament.model.Group;
import com.badminton.tournament.model.Tournament;
import com.badminton.tournament.repository.CategoryRepository;
import com.badminton.tournament.repository.GroupRepository;
import com.badminton.tournament.repository.OrganizerRepository;
import com.badminton.tournament.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final OrganizerRepository organizerRepository;
    private final CategoryRepository categoryRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public TournamentDTO create(TournamentRequestDTO dto, Long organizerId) {
        Tournament tournament = new Tournament();
        tournament.setTitle(dto.getTitle());
        tournament.setDescription(dto.getDescription());
        tournament.setStartDate(dto.getStartDate());
        tournament.setEndDate(dto.getEndDate());
        tournament.setCity(dto.getCity());
        tournament.setVenue(dto.getVenue());
        tournament.setAddress(dto.getAddress());
        tournament.setMinAge(dto.getMinAge());
        tournament.setMaxAge(dto.getMaxAge());
        tournament.setMaxParticipants(dto.getMaxParticipants());
        tournament.setRegistrationDeadline(dto.getRegistrationDeadline());
        tournament.setStatus("DRAFT");
        tournament.setOrganizer(organizerRepository.findById(organizerId)
                .orElseThrow(() -> new ResourceNotFoundException("Организатор не найден")));

        // Сохраняем категории
        Set<Category> categories = dto.getCategoryIds().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена: " + id)))
                .collect(Collectors.toSet());
        tournament.setCategories(categories);

        // Сохраняем турнир сначала, чтобы получить ID
        tournament = tournamentRepository.save(tournament);

        // СОЗДАЁМ ГРУППЫ ДИНАМИЧЕСКИ (а не ищем существующие)
        Set<Group> groups = new HashSet<>();
        for (String code : dto.getGroupCodes()) {
            Group group = new Group();
            group.setTournament(tournament);
            group.setCode(code);
            group.setName("Группа " + code);
            group.setMaxTeams(4);
            group.setTeamsCount(0);
            group.setIsCompleted(false);
            groups.add(groupRepository.save(group));
        }
        tournament.setGroups(groups);

        return toDTO(tournament);
    }

    public List<TournamentDTO> findAll(String city, String status) {
        List<Tournament> tournaments;
        if (city != null || status != null) {
            tournaments = tournamentRepository.findByFilters(city, status, null, null, null);
        } else {
            tournaments = tournamentRepository.findAll();
        }
        return tournaments.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TournamentDTO findById(Long id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Турнир не найден"));
        return toDTO(tournament);
    }

    @Transactional
    public void updateStatus(Long id, String status) {
        tournamentRepository.updateStatus(id, status);
    }

    private TournamentDTO toDTO(Tournament t) {
        return TournamentDTO.builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .status(t.getStatus())
                .organizerId(t.getOrganizer().getId())
                .organizerName(t.getOrganizer().getName())
                .organizerCity(t.getOrganizer().getCity())
                .startDate(t.getStartDate())
                .endDate(t.getEndDate())
                .registrationDeadline(t.getRegistrationDeadline())
                .city(t.getCity())
                .venue(t.getVenue())
                .address(t.getAddress())
                .minAge(t.getMinAge())
                .maxAge(t.getMaxAge())
                .maxParticipants(t.getMaxParticipants())
                .categories(t.getCategories().stream().map(Category::getCode).collect(Collectors.toList()))
                .groups(t.getGroups().stream().map(Group::getCode).collect(Collectors.toList()))
                .registrationOpen(t.isRegistrationOpen())
                .isCompleted(t.isCompleted())
                .build();
    }
}