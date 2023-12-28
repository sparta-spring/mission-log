package com.sparta.missionreport.domain.card.service;

import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.card.repository.CardRepository;
import com.sparta.missionreport.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {

    private final CardRepository cardRepository;

    public void createCard(User user, Long boardId, Long columnId, CardDto.Request request) {


    }
}
