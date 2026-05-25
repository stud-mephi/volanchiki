package com.badminton.tournament.service;

import com.badminton.tournament.dto.RatingDTO;
import com.badminton.tournament.dto.RatingHistoryDTO;
import com.badminton.tournament.repository.RatingRepository;
import com.badminton.tournament.repository.RatingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingHistoryRepository ratingHistoryRepository;

    public List<RatingDTO> getTopPlayers(String categoryType, String categoryGender, int limit, int minGames) {
        return ratingRepository.findTopRatings(categoryType, categoryGender, minGames,
                PageRequest.of(0, limit)).stream()
                .map(r -> toDTO(r, 0))
                .collect(Collectors.toList());
    }

    public List<RatingHistoryDTO> getUserHistory(Long userId) {
        return ratingHistoryRepository.findByUserId(userId).stream()
                .map(this::toHistoryDTO)
                .collect(Collectors.toList());
    }

    private RatingDTO toDTO(com.badminton.tournament.model.Rating r, int position) {
        return RatingDTO.builder()
                .userId(r.getUserId())
                .fullName(r.getUser().getFullName())
                .nickname(r.getUser().getNickname())
                .city(r.getUser().getCity())
                .categoryType(r.getCategoryType())
                .categoryGender(r.getCategoryGender())
                .categoryDisplay(r.getCategoryDisplay())
                .ratingValue(r.getRatingValue())
                .position(position)
                .gamesPlayed(r.getGamesPlayed())
                .wins(r.getWins())
                .losses(r.getLosses())
                .winRate(r.getWinRate() != null ? r.getWinRate().doubleValue() : null)
                .lastUpdated(r.getLastUpdated())
                .build();
    }

    private RatingHistoryDTO toHistoryDTO(com.badminton.tournament.model.RatingHistory h) {
        return RatingHistoryDTO.builder()
                .id(h.getId())
                .changeDate(h.getChangeDate())
                .formattedDate(h.getFormattedChangeDate())
                .categoryType(h.getCategoryType())
                .categoryGender(h.getCategoryGender())
                .oldRating(h.getOldRating())
                .newRating(h.getNewRating())
                .changeValue(h.getChangeValue())
                .changeSign(h.getChangeSign())
                .reason(h.getReason())
                .tournamentId(h.getTournamentId())
                .isIncrease(h.isIncrease())
                .isDecrease(h.isDecrease())
                .build();
    }
}
