package com.sparta.missionreport.domain.card.service;

import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.repository.CardRepository;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.column.service.ColumnsService;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {

    private final ColumnsService columnsService;
    private final UserService userService;
    private final CardRepository cardRepository;

    public CardDto.Response createCard(User user, Long columnId, CardDto.Request request) {
        Columns columns = columnsService.findColumns(columnId);
        User createdBy = userService.findUser(user.getId());

        long sequence = cardRepository.countByColumns_Id(columnId);
        Card savedCard = cardRepository.save(request.toEntity(sequence, columns, createdBy));

        return CardDto.Response.of(savedCard);
    }
}
