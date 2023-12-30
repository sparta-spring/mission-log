package com.sparta.missionreport.domain.card.service;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.service.BoardService;
import com.sparta.missionreport.domain.board.service.BoardWorkerService;
import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.card.dto.CardWorkerDto;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.entity.CardWorker;
import com.sparta.missionreport.domain.card.exception.CardCustomException;
import com.sparta.missionreport.domain.card.exception.CardExceptionCode;
import com.sparta.missionreport.domain.card.repository.CardRepository;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.column.service.ColumnsService;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.service.UserService;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final UserService userService;
    private final BoardService boardService;
    private final ColumnsService columnsService;
    private final CardWorkerService cardWorkerService;
    private final BoardWorkerService boardWorkerService;
    private final CardRepository cardRepository;

    @Transactional
    public CardDto.CardResponse createCard(User user, Long columnId,
                                           CardDto.CreateCardRequest createCardRequest) {
        Columns columns = columnsService.findColumns(columnId);
        User createdBy = userService.findUserById(user.getId());

        if (!boardWorkerService.isExistedWorker(createdBy, columns.getBoard())) {
            throw new CardCustomException(CardExceptionCode.NOT_AUTHORIZATION_ABOUT_REQUEST);
        }

        long sequence = cardRepository.countByColumns_IdAndIsDeletedIsFalse(columnId) + 1;
        Card savedCard = cardRepository.save(createCardRequest.toEntity(sequence, columns, createdBy));

        cardWorkerService.saveCardWorker(savedCard, createdBy);

        return CardDto.CardResponse.of(savedCard);
    }


    @Transactional
    public CardDto.CardResponse update(User user, Long cardId, CardDto.UpdateCardRequest updateCardRequest) {
        Card card = getCardAndCheckAuth(user, cardId);

        card.update(updateCardRequest);
        return CardDto.CardResponse.of(card);
    }

    @Transactional
    public void deleteCard(User user, Long cardId) {
        Card card = getCardAndCheckAuth(user, cardId);
        card.deleteCard();
        cardWorkerService.deleteWorkers(card);

        long sequence = card.getSequence();
        long last = cardRepository.findTopByColumns_IdAndIsDeletedIsFalseOrderBySequenceDesc(
                        card.getColumns().getId())
                .orElseThrow(() -> new CardCustomException(CardExceptionCode.CARD_NOT_FOUND))
                .getSequence();

        cardRepository.decreaseSequence(card.getColumns().getId(), sequence, last);
    }

    public List<CardDto.CardResponse> getCardsByBoard(User user, Long boardId) {
        Board board = boardService.findBoardByID(boardId);
        User loginUser = userService.findUserById(user.getId());
        if (!boardWorkerService.isExistedWorker(loginUser, board)) {
            throw new CardCustomException(CardExceptionCode.NOT_AUTHORIZATION_ABOUT_REQUEST);
        }

        List<Card> cards = cardRepository.findAllByColumns_Board_IdAndIsDeletedIsFalse(boardId);
        return cards.stream().map(CardDto.CardResponse::of).toList();
    }

    public List<CardDto.CardResponse> getCardsByColumn(User user, Long columnsId) {
        Columns columns = columnsService.findColumns(columnsId);
        User loginUser = userService.findUserById(user.getId());
        Board board = columns.getBoard();

        if (boardWorkerService.isExistedWorker(loginUser, board)) {
            List<Card> list = cardRepository.findAllByColumns_IdAndIsDeletedIsFalseOrderBySequence(columnsId);

            return list.stream().map(CardDto.CardResponse::of).toList();
        }
        return null;
    }

    public CardDto.CardResponse getCard(User user, Long cardId) {
        Card card = getCardAndCheckAuth(user, cardId);
        return CardDto.CardResponse.of(card);
    }

    @Transactional
    public void inviteUser(User user, Long cardId, CardWorkerDto.CardWorkerInviteRequest requestDto) {
        Card card = getCardAndCheckAuth(user, cardId);
        User requestUser = userService.findUserByEmail(requestDto.getEmail());

        if (cardWorkerService.isExistedWorker(requestUser, card)) {
            throw new CardCustomException(CardExceptionCode.ALREADY_INVITED_USER);
        }

        if (!boardWorkerService.isExistedWorker(requestUser, card.getColumns().getBoard())) {
            throw new CardCustomException(CardExceptionCode.NO_INVITED_REQUEST_USER);
        }
        cardWorkerService.saveCardWorker(card, requestUser);
    }

    public List<CardWorkerDto.CardWorkerResponse> getWorkersInCard(User user, Long cardId) {
        Card card = getCardAndCheckAuth(user, cardId);

        List<CardWorker> list = cardWorkerService.findAllByWorkersInCard(card);
        return list.stream().map(CardWorkerDto.CardWorkerResponse::of).toList();
    }

    public CardWorkerDto.CardWorkerResponse searchWorkerInCard(User user, Long cardId, String email) {
        Card card = getCardAndCheckAuth(user, cardId);
        User searchUser = userService.findUserByEmail(email);

        CardWorker cardWorker = cardWorkerService.searchUser(card, searchUser);
        return CardWorkerDto.CardWorkerResponse.of(cardWorker);
    }

    @Transactional
    public CardDto.CardResponse moveCardInSameColumn(User user, Long cardId, CardDto.MoveCardInSameColRequest request) {
        Card card = getCardAndCheckAuth(user, cardId);
        Columns columns = card.getColumns();

        if (request.getSequence() < 1 || columns.getCardList().size() < request.getSequence()) {
            throw new CardCustomException(CardExceptionCode.INVALID_UPDATE_SEQUENCE);
        }

        Long curSequence = card.getSequence();
        Long changeSequence = request.getSequence();

        if (changeSequence > curSequence) {
            cardRepository.decreaseSequence(columns.getId(), curSequence, changeSequence);
        } else {
            cardRepository.increaseSequence(columns.getId(), changeSequence, curSequence);
        }

        card.updateSequence(changeSequence);

        return CardDto.CardResponse.of(card);
    }

    @Transactional
    public CardDto.CardResponse moveCard(User user, Long cardId, CardDto.MoveCardRequest request) {
        Card card = getCardAndCheckAuth(user, cardId);
        Columns nowColumn = card.getColumns();
        Columns changeColumn = columnsService.findColumns(request.getColumn_id());

        if (request.getSequence() < 1 || changeColumn.getCardList().size() + 1 < request.getSequence()) {
            throw new CardCustomException(CardExceptionCode.INVALID_UPDATE_SEQUENCE);
        }

        Long curSequence = card.getSequence();
        Long last = cardRepository.findTopByColumns_IdAndIsDeletedIsFalseOrderBySequenceDesc(
                        nowColumn.getId())
                .orElseThrow(() -> new CardCustomException(CardExceptionCode.CARD_NOT_FOUND))
                .getSequence();

        cardRepository.decreaseSequence(nowColumn.getId(), curSequence, last);

        Card findCard = cardRepository.findTopByColumns_IdAndIsDeletedIsFalseOrderBySequenceDesc(
                        changeColumn.getId())
                .orElse(null);

        if (findCard == null) last = 0L;
        else last = findCard.getSequence();

        cardRepository.increaseSequence(changeColumn.getId(), request.getSequence(), last + 1);

        card.updateSequenceAndColumn(changeColumn, request.getSequence());

        return CardDto.CardResponse.of(card);
    }

    @Transactional
    public void deleteWorker(User user, Long cardId) {
        Card card = getCardAndCheckAuth(user, cardId);
        User worker = userService.findUserById(user.getId());

        if (!cardWorkerService.isExistedWorker(worker, card)) {
            throw new CardCustomException(CardExceptionCode.NOT_FOUND_WORKER_IN_CARD);
        }

        cardWorkerService.deleteWorker(card, worker);
    }

    public Card getCardAndCheckAuth(User user, Long cardId) {
        Card card = findCardById(cardId);
        User loginUser = userService.findUserById(user.getId());

        if (!cardWorkerService.isExistedWorker(loginUser, card)) {
            throw new CardCustomException(CardExceptionCode.NOT_AUTHORIZATION_ABOUT_CARD);
        }

        return card;
    }


    public Card findCardById(Long cardId) {
        return cardRepository.findByIdAndIsDeletedIsFalse(cardId).orElseThrow(
                () -> new CardCustomException(CardExceptionCode.CARD_NOT_FOUND)
        );
    }
}
