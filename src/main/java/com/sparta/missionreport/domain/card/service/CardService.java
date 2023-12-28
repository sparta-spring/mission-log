package com.sparta.missionreport.domain.card.service;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.service.BoardService;
import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.exception.CardCustomException;
import com.sparta.missionreport.domain.card.exception.CardExceptionCode;
import com.sparta.missionreport.domain.card.repository.CardRepository;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.column.service.ColumnsService;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final ColumnsService columnsService;
    private final UserService userService;
    private final CardWorkerService cardWorkerService;
    private final BoardService boardService;
    private final CardRepository cardRepository;

    @Transactional
    public CardDto.Response createCard(User user, Long columnId, CardDto.Request request) {
        Columns columns = columnsService.findColumns(columnId);
        User createdBy = userService.findUser(user.getId());

        long sequence = cardRepository.countByColumns_Id(columnId);
        Card savedCard = cardRepository.save(request.toEntity(sequence, columns, createdBy));

        cardWorkerService.saveCardWorker(savedCard, createdBy);

        return CardDto.Response.of(savedCard);
    }

    @Transactional
    public CardDto.Response updateName(User user, Long cardId, CardDto.NameRequest nameRequest) {
        Card card = getCardAndCheckAuth(user, cardId);

        card.updateName(nameRequest);
        return CardDto.Response.of(card);
    }

    @Transactional
    public CardDto.Response updateColor(User user, Long cardId, CardDto.ColorRequest colorRequest) {
        Card card = getCardAndCheckAuth(user, cardId);

        card.updateColor(colorRequest);
        return CardDto.Response.of(card);
    }

    @Transactional
    public CardDto.Response updateDescription(User user, Long cardId, CardDto.DescriptionRequest descriptionRequest) {
        Card card = getCardAndCheckAuth(user, cardId);

        card.updateDescription(descriptionRequest);
        return CardDto.Response.of(card);
    }

    @Transactional
    public CardDto.Response updateDeadLine(User user, Long cardId, CardDto.DeadLineRequest deadLineRequest) {
        Card card = getCardAndCheckAuth(user, cardId);

        card.updateDeadLine(deadLineRequest);
        return CardDto.Response.of(card);
    }

    @Transactional
    public void deleteCard(User user, Long cardId) {
        Card card = getCardAndCheckAuth(user, cardId);
        cardRepository.delete(card);
    }

    public List<CardDto.Response> getCardsByBoard(User user, Long boardId) {
        Board board = boardService.findBoard(boardId);
        /* TODO: 로그인 유저가 해당 보드 작업자 여부 확인 코드 작성*/

        List<Card> cards = cardRepository.findAllByColumns_Board_Id(boardId);
        return cards.stream().map(CardDto.Response::of).toList();
    }

    public CardDto.Response getCard(User user, Long cardId) {
        Card card = getCardAndCheckAuth(user, cardId);
        return CardDto.Response.of(card);
    }

    private Card getCardAndCheckAuth(User user, Long cardId) {
        Card card = findCard(cardId);
        User loginUser = userService.findUser(user.getId());

        if (!cardWorkerService.isExistedWorker(loginUser, card)) {
            throw new CardCustomException(CardExceptionCode.NOT_AUTHORIZATION_ABOUT_CARD);
        }
        return card;
    }


    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                () -> new CardCustomException(CardExceptionCode.CARD_NOT_FOUND)
        );
    }
}
