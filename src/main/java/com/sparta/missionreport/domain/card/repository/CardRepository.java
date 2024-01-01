package com.sparta.missionreport.domain.card.repository;

import com.sparta.missionreport.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {

    Optional<Card> findByIdAndIsDeletedIsFalse(Long card_id);
    List<Card> findAllByColumns_IdAndIsDeletedIsFalseOrderBySequence(Long column_id);
    Long countByColumns_IdAndIsDeletedIsFalse(Long columns_id);

    List<Card> findAllByColumns_Board_IdAndIsDeletedIsFalse(Long board_id);

    Optional<Card> findTopByColumns_IdAndIsDeletedIsFalseOrderBySequenceDesc(Long columns_id);
}
