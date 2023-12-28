package com.sparta.missionreport.domain.card.repository;

import com.sparta.missionreport.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
