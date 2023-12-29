package com.sparta.missionreport.domain.card.repository;

import com.sparta.missionreport.domain.card.entity.CardWorker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardWorkerRepository extends JpaRepository<CardWorker, Long> {

    Boolean existsByUser_IdAndCard_IdAndIsDeletedIsFalse(Long userId, Long cardId);

    List<CardWorker> findAllByCard_IdAndIsDeletedIsFalse(Long cardId);

    Optional<CardWorker> findByCard_IdAndIsDeletedIsFalseAndUser_Email(Long userId, String email);
}
