package com.badminton.tournament.service;

import com.badminton.tournament.dto.PairDTO;
import com.badminton.tournament.exception.BadRequestException;
import com.badminton.tournament.exception.ResourceNotFoundException;
import com.badminton.tournament.model.Pair;
import com.badminton.tournament.model.User;
import com.badminton.tournament.repository.PairRepository;
import com.badminton.tournament.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PairService {

    private final PairRepository pairRepository;
    private final UserRepository userRepository;

    @Transactional
    public PairDTO create(Long player1Id, Long player2Id, String pairType) {
        if (player1Id.equals(player2Id)) {
            throw new BadRequestException("Нельзя создать пару с самим собой");
        }

        User player1 = userRepository.findById(player1Id)
                .orElseThrow(() -> new ResourceNotFoundException("Игрок 1 не найден"));
        User player2 = userRepository.findById(player2Id)
                .orElseThrow(() -> new ResourceNotFoundException("Игрок 2 не найден"));

        if (pairRepository.existsByPlayer1IdAndPlayer2IdAndPairType(
                Math.min(player1Id, player2Id), Math.max(player1Id, player2Id), pairType)) {
            throw new BadRequestException("Такая пара уже существует");
        }

        Pair pair = new Pair();
        pair.setPlayer1(player1Id < player2Id ? player1 : player2);
        pair.setPlayer2(player1Id < player2Id ? player2 : player1);
        pair.setPairType(pairType);
        pair = pairRepository.save(pair);
        return toDTO(pair);
    }

    public List<PairDTO> findByUser(Long userId) {
        return pairRepository.findByUserId(userId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    private PairDTO toDTO(Pair p) {
        return PairDTO.builder()
                .id(p.getId())
                .player1Id(p.getPlayer1().getId())
                .player1Name(p.getPlayer1().getFullName())
                .player2Id(p.getPlayer2().getId())
                .player2Name(p.getPlayer2().getFullName())
                .pairType(p.getPairType())
                .pairName(p.getPairName())
                .pairRating(p.getPairRating())
                .gamesPlayed(p.getGamesPlayed())
                .wins(p.getWins())
                .losses(p.getLosses())
                .isActive(p.getIsActive())
                .build();
    }
}
