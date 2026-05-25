package com.badminton.tournament.service;

import com.badminton.tournament.dto.BracketMatchDTO;
import com.badminton.tournament.model.BracketMatch;
import com.badminton.tournament.model.Registration;
import com.badminton.tournament.repository.BracketMatchRepository;
import com.badminton.tournament.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.badminton.tournament.exception.BadRequestException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BracketService {

    private final BracketMatchRepository bracketMatchRepository;
    private final RegistrationRepository registrationRepository;

    public List<BracketMatchDTO> getBracket(Long tournamentId, Integer categoryId) {
        return bracketMatchRepository.findByTournamentIdAndCategoryId(tournamentId, categoryId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<BracketMatchDTO> generateBracket(Long tournamentId, Integer categoryId) {
        List<Registration> registrations = registrationRepository
                .findByTournamentIdAndCategoryIdAndStatus(tournamentId, categoryId, "CONFIRMED");

        if (registrations.size() < 2) {
            throw new BadRequestException("Недостаточно участников (минимум 2)");
        }

        Collections.shuffle(registrations);
        int roundCount = (int) (Math.log(registrations.size()) / Math.log(2));
        int totalSlots = (int) Math.pow(2, roundCount + 1);

        List<Registration> slots = new ArrayList<>(registrations);
        while (slots.size() < totalSlots) slots.add(null);

        List<BracketMatch> allMatches = new ArrayList<>();
        int currentRound = roundCount + 1;

        for (int round = 0; round <= roundCount; round++) {
            int matchesInRound = (int) Math.pow(2, roundCount - round);
            for (int i = 0; i < matchesInRound; i++) {
                BracketMatch match = new BracketMatch();
                match.setTournament(registrations.get(0).getTournament());
                match.setCategory(registrations.get(0).getCategory());
                match.setRoundNumber(currentRound);
                match.setRoundName(getRoundName(currentRound));
                match.setMatchOrder(i + 1);
                match.setStatus("SCHEDULED");
                if (round == 0) {
                    match.setPlayer1Registration(slots.get(i * 2));
                    match.setPlayer2Registration(slots.get(i * 2 + 1));
                }
                allMatches.add(match);
            }
            currentRound--;
        }

        allMatches = bracketMatchRepository.saveAll(allMatches);
        return allMatches.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private String getRoundName(int round) {
        switch (round) {
            case 1: return "FINAL";
            case 2: return "SEMIFINAL";
            case 3: return "QUARTERFINAL";
            default: return "1/" + (int) Math.pow(2, round - 1);
        }
    }

    private BracketMatchDTO toDTO(BracketMatch m) {
        return BracketMatchDTO.builder()
                .id(m.getId())
                .tournamentId(m.getTournament().getId())
                .categoryId(m.getCategory().getId())
                .categoryCode(m.getCategory().getCode())
                .roundName(m.getRoundName())
                .roundNumber(m.getRoundNumber())
                .matchOrder(m.getMatchOrder())
                .player1Name(m.getPlayer1Registration() != null ? m.getPlayer1Registration().getUser().getFullName() : "BYE")
                .player2Name(m.getPlayer2Registration() != null ? m.getPlayer2Registration().getUser().getFullName() : "BYE")
                .status(m.getStatus())
                .nextMatchId(m.getNextMatchId())
                .build();
    }
}

