package com.sparta.missionreport.domain.card.service;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.entity.CardWorker;
import com.sparta.missionreport.domain.card.exception.CardCustomException;
import com.sparta.missionreport.domain.card.exception.CardExceptionCode;
import com.sparta.missionreport.domain.card.repository.CardWorkerRepository;
import com.sparta.missionreport.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardWorkerService {

    private final CardWorkerRepository cardWorkerRepository;

    public boolean isExistedWorker(User user, Card card) {
        return cardWorkerRepository.existsByUser_IdAndCard_IdAndIsDeletedIsFalse(user.getId(), card.getId());
    }

    @Transactional
    public void saveCardWorker(Card savedCard, User createdBy) {
        CardWorker cardWorker = CardWorker.builder().user(createdBy).card(savedCard).build();
        cardWorkerRepository.save(cardWorker);
    }

    @Transactional
    public void deleteWorkers(Card card) {
        List<CardWorker> list = cardWorkerRepository.findAllByCard_IdAndIsDeletedIsFalse(card.getId());
        for (CardWorker cardWorker : list) {
            cardWorker.delete();
        }
    }

    public List<CardWorker> findAllByWorkersInCard(Card card) {
        return cardWorkerRepository.findAllByCard_IdAndIsDeletedIsFalse(card.getId());
    }

    public CardWorker searchUser(Card card, User searchUser) {
        return cardWorkerRepository.findByCard_IdAndIsDeletedIsFalseAndUser_Email(card.getId(), searchUser.getEmail()).orElseThrow(
                () -> new CardCustomException(CardExceptionCode.NOT_FOUND_WORKER_IN_CARD)
        );
    }

    @Transactional
    public void deleteWorker(Card card, User worker) {
        CardWorker cardWorker = cardWorkerRepository.findCardWorkerByCard_IdAndUser_Id(card.getId(), worker.getId()).orElseThrow(
                () -> new CardCustomException(CardExceptionCode.NOT_FOUND_WORKER_IN_CARD)
        );

        cardWorker.delete();
    }
}
