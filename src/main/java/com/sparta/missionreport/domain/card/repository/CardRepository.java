package com.sparta.missionreport.domain.card.repository;

import com.sparta.missionreport.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    Long countByColumns_Id(Long columns_id);

    List<Card> findAllByColumns_Board_Id(Long board_id);
}
