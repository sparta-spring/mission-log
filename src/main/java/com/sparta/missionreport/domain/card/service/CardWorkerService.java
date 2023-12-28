package com.sparta.missionreport.domain.card.service;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.entity.CardWorker;
import com.sparta.missionreport.domain.card.repository.CardWorkerRepository;
import com.sparta.missionreport.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardWorkerService {

    private final CardWorkerRepository cardWorkerRepository;

    public boolean isExistedWorker(User user, Card card) {
        return cardWorkerRepository.existsByUser_IdAndCard_Id(user.getId(), card.getId());
    }

    @Transactional
    public void saveCardWorker(Card savedCard, User createdBy) {
        CardWorker cardWorker = CardWorker.builder().user(createdBy).card(savedCard).build();
        cardWorkerRepository.save(cardWorker);
    }
}
