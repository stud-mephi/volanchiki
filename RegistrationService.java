package com.badminton.tournament.service;

import com.badminton.tournament.dto.RegistrationRequestDTO;
import com.badminton.tournament.dto.RegistrationResponseDTO;
import com.badminton.tournament.model.Registration;
import com.badminton.tournament.model.Tournament;
import com.badminton.tournament.model.User;
import com.badminton.tournament.model.Category;
import com.badminton.tournament.repository.RegistrationRepository;
import com.badminton.tournament.repository.TournamentRepository;
import com.badminton.tournament.repository.UserRepository;
import com.badminton.tournament.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.badminton.tournament.exception.BadRequestException;
import com.badminton.tournament.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public RegistrationResponseDTO register(Long userId, RegistrationRequestDTO dto) {
        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new ResourceNotFoundException("Турнир не найден"));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));

        if (registrationRepository.existsByUserIdAndTournamentIdAndCategoryId(
                userId, dto.getTournamentId(), dto.getCategoryId())) {
            throw new BadRequestException("Вы уже зарегистрированы в этой категории");
        }

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setTournament(tournament);
        registration.setCategory(category);
        registration.setStatus("PENDING");
        registration.setTeamName(dto.getTeamName());
        registration.setComment(dto.getComment());

        if (dto.getPartnerId() != null) {
            User partner = userRepository.findById(dto.getPartnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Партнёр не найден"));
            registration.setPartner(partner);
        }

        registration = registrationRepository.save(registration);
        return toDTO(registration);
    }

    public List<RegistrationResponseDTO> findByUser(Long userId) {
        return registrationRepository.findByUserId(userId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<RegistrationResponseDTO> findByTournament(Long tournamentId) {
        return registrationRepository.findByTournamentId(tournamentId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void confirm(Long registrationId) {
        registrationRepository.confirmRegistration(registrationId, "CONFIRMED");
    }

    @Transactional
    public void reject(Long registrationId) {
        registrationRepository.updateStatus(registrationId, "REJECTED");
    }

    private RegistrationResponseDTO toDTO(Registration r) {
        return RegistrationResponseDTO.builder()
                .id(r.getId())
                .status(r.getStatus())
                .userId(r.getUser().getId())
                .userFullName(r.getUser().getFullName())
                .userNickname(r.getUser().getNickname())
                .tournamentId(r.getTournament().getId())
                .tournamentTitle(r.getTournament().getTitle())
                .categoryId(r.getCategory().getId())
                .categoryCode(r.getCategory().getCode())
                .categoryName(r.getCategory().getName())
                .partnerId(r.getPartner() != null ? r.getPartner().getId() : null)
                .partnerName(r.getPartner() != null ? r.getPartner().getFullName() : null)
                .teamName(r.getTeamName())
                .registeredAt(r.getRegisteredAt())
                .place(r.getPlace())
                .seed(r.getSeed())
                .groupName(r.getGroupName())
                .build();
    }
}
