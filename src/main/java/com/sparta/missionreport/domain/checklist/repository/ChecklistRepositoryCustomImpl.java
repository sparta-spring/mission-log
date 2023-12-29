package com.sparta.missionreport.domain.checklist.repository;

import static com.sparta.missionreport.domain.checklist.entity.QChecklist.checklist;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChecklistRepositoryCustomImpl implements ChecklistRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void decreaseSequence(Long cardId, Long start, Long end) {
        jpaQueryFactory.update(checklist)
                .where(checklist.card.id.eq(cardId)
                        .and(checklist.sequence.between(start + 1, end))
                )
                .set(checklist.sequence, checklist.sequence.subtract(1))
                .execute();
    }

}
