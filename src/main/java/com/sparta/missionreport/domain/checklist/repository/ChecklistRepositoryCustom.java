package com.sparta.missionreport.domain.checklist.repository;

public interface ChecklistRepositoryCustom {

    void decreaseSequence(Long cardId, Long start, Long end);

    void increaseSequence(Long cardId, Long start, Long end);

}
