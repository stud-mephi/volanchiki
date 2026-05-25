package com.badminton.tournament.repository;

import com.badminton.tournament.model.RatingAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RatingAdjustmentRepository extends JpaRepository<RatingAdjustment, Long> {

    // Поиск по пользователю
    List<RatingAdjustment> findByUserId(Long userId);

    // Поиск по организатору
    List<RatingAdjustment> findByOrganizerId(Long organizerId);

    // Неподтверждённые корректировки
    List<RatingAdjustment> findByIsApprovedFalse();

    // Подтверждение корректировки
    @Modifying
    @Transactional
    @Query("UPDATE RatingAdjustment a SET a.isApproved = true, " +
           "a.approvedBy.id = :approvedById, a.approvedDate = CURRENT_TIMESTAMP " +
           "WHERE a.id = :id")
    void approve(@Param("id") Long id, @Param("approvedById") Long approvedById);

    // Отклонение (просто удаляем)
    @Modifying
    @Transactional
    void deleteByIdAndIsApprovedFalse(Long id);
}

