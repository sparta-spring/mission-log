package com.sparta.missionreport.domain.card.repository;

public interface CardRepositoryCustom {

    void decreaseSequence(Long column_id, Long start, Long end);
}
