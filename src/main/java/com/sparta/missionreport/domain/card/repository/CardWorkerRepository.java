package com.sparta.missionreport.domain.card.repository;

import com.sparta.missionreport.domain.card.entity.CardWorker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardWorkerRepository extends JpaRepository<CardWorker, Long> {

    Boolean existsByUser_IdAndCard_Id(Long userId, Long cardId);
}
