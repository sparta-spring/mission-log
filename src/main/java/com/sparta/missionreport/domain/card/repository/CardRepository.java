package com.sparta.missionreport.domain.card.repository;

import com.sparta.missionreport.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {

    Long countByColumns_IdAndIsDeletedIsFalse(Long columns_id);

    List<Card> findAllByColumns_Board_IdAndIsDeletedIsFalse(Long board_id);
}
