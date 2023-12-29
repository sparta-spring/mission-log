package com.sparta.missionreport.domain.card.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sparta.missionreport.domain.card.entity.QCard.card;

@Repository
@RequiredArgsConstructor
public class CardRepositoryCustomImpl implements CardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public void decreaseSequence(Long column_id, Long start, Long end) {
        jpaQueryFactory.update(card)
                .where(card.columns.id.eq(column_id)
                        .and(card.sequence.between(start + 1, end))
                )
                .set(card.sequence, card.sequence.subtract(1))
                .execute();
    }
}
