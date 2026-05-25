package com.badminton.tournament.service;

import com.badminton.tournament.dto.MatchDTO;
import com.badminton.tournament.dto.MatchResultDTO;
import com.badminton.tournament.exception.BadRequestException;
import com.badminton.tournament.exception.ResourceNotFoundException;
import com.badminton.tournament.model.Match;
import com.badminton.tournament.model.Rating;
import com.badminton.tournament.model.RatingHistory;
import com.badminton.tournament.model.Registration;
import com.badminton.tournament.repository.MatchRepository;
import com.badminton.tournament.repository.RatingHistoryRepository;
import com.badminton.tournament.repository.RatingRepository;
import com.badminton.tournament.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final RegistrationRepository registrationRepository;
    private final RatingRepository ratingRepository;
    private final RatingHistoryRepository ratingHistoryRepository;

    public List<MatchDTO> findByTournament(Long tournamentId) {
        return matchRepository.findByTournamentIdOrderByMatchNumberAsc(tournamentId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public MatchDTO submitResult(MatchResultDTO dto) {
        Match match = matchRepository.findById(dto.getMatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Матч не найден"));

        if (Boolean.TRUE.equals(match.getProcessed())) {
            throw new BadRequestException("Результат этого матча уже внесён");
        }

        Registration winner = registrationRepository.findById(dto.getWinnerRegistrationId())
                .orElseThrow(() -> new ResourceNotFoundException("Победитель не найден"));

        // Определяем кто победитель, кто проигравший
        // и берём их рейтинги ДО матча правильно
        boolean winnerIsPlayer1 = match.getRegistration1().equals(winner);
        Registration loser = winnerIsPlayer1
                ? match.getRegistration2()
                : match.getRegistration1();

        int winnerRating = winnerIsPlayer1
                ? match.getRating1Before()
                : match.getRating2Before();
        int loserRating = winnerIsPlayer1
                ? match.getRating2Before()
                : match.getRating1Before();

        // Формула: дельта = [100 - (РВ - РП)] / 10, минимум 1, максимум 20
        int delta = calculateDelta(winnerRating, loserRating);

        // Обновляем рейтинги
        updateRating(winner.getUser().getId(),
                winner.getCategory().getType(),
                winner.getCategory().getGender(), delta);
        updateRating(loser.getUser().getId(),
                loser.getCategory().getType(),
                loser.getCategory().getGender(), -delta);

        // Сохраняем результат матча
        match.setWinner(winner);
        match.setScore(dto.getScore());
        match.setDelta(delta);
        match.setProcessed(true);
        matchRepository.save(match);

        return toDTO(match);
    }

    // дельта = [100 - (РВ - РП)] / 10
    int calculateDelta(int winnerRating, int loserRating) {
        int delta = (100 - (winnerRating - loserRating)) / 10;
        if (delta < 1)  return 1;
        if (delta > 20) return 20;
        return delta;
    }

    private void updateRating(Long userId, String categoryType,
                               String categoryGender, int delta) {
        Rating rating = ratingRepository
                .findByUserIdAndCategoryTypeAndCategoryGender(userId, categoryType, categoryGender)
                .orElseGet(() -> {
                    Rating r = new Rating();
                    r.setUserId(userId);
                    r.setCategoryType(categoryType);
                    r.setCategoryGender(categoryGender);
                    r.setRatingValue(100);
                    r.setGamesPlayed(0);
                    r.setWins(0);
                    r.setLosses(0);
                    return r;
                });

        int oldRating = rating.getRatingValue();
        // Рейтинг не может быть ниже 1
        int newRating = Math.max(1, oldRating + delta);
        rating.setRatingValue(newRating);

        if (delta > 0) rating.addWin();
        else           rating.addLoss();

        ratingRepository.save(rating);

        // Записываем в историю
        RatingHistory history = new RatingHistory();
        history.setUserId(userId);
        history.setCategoryType(categoryType);
        history.setCategoryGender(categoryGender);
        history.setOldRating(oldRating);
        history.setNewRating(newRating);
        history.setChangeValue(newRating - oldRating);
        ratingHistoryRepository.save(history);
    }

    private MatchDTO toDTO(Match m) {
        return MatchDTO.builder()
                .id(m.getId())
                .tournamentId(m.getTournament().getId())
                .tournamentTitle(m.getTournament().getTitle())
                .matchRound(m.getMatchRound())
                .matchNumber(m.getMatchNumber())
                .registrationId1(m.getRegistration1().getId())
                .player1Name(m.getPlayer1Name())
                .registrationId2(m.getRegistration2().getId())
                .player2Name(m.getPlayer2Name())
                .winnerId(m.getWinner() != null ? m.getWinner().getId() : null)
                .winnerName(m.getWinnerName())
                .score(m.getScore())
                .delta(m.getDelta())
                .processed(m.getProcessed())
                .matchDate(m.getMatchDate())
                .build();
    }
}
