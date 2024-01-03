package com.sparta.missionreport.domain.card.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.missionreport.DatabaseCleaner;
import com.sparta.missionreport.domain.board.dto.BoardDto;
import com.sparta.missionreport.domain.board.dto.BoardWorkerDto;
import com.sparta.missionreport.domain.board.repository.BoardRepository;
import com.sparta.missionreport.domain.board.repository.BoardWorkerRepository;
import com.sparta.missionreport.domain.board.service.BoardService;
import com.sparta.missionreport.domain.board.service.BoardWorkerService;
import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.card.dto.CardWorkerDto;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.exception.CardCustomException;
import com.sparta.missionreport.domain.card.exception.CardExceptionCode;
import com.sparta.missionreport.domain.card.repository.CardRepository;
import com.sparta.missionreport.domain.card.repository.CardWorkerRepository;
import com.sparta.missionreport.domain.column.dto.ColumnsRequestDto;
import com.sparta.missionreport.domain.column.dto.ColumnsResponseDto;
import com.sparta.missionreport.domain.column.exception.ColumnsCustomException;
import com.sparta.missionreport.domain.column.exception.ColumnsExceptionCode;
import com.sparta.missionreport.domain.column.service.ColumnsService;
import com.sparta.missionreport.domain.user.dto.UserDto;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.repository.UserRepository;
import com.sparta.missionreport.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Card Service 테스트")
class CardServiceTest {

    @Autowired
    CardService sut;
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;
    @Autowired
    ColumnsService columnsService;
    @Autowired
    CardWorkerService cardWorkerService;
    @Autowired
    BoardWorkerService boardWorkerService;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    DatabaseCleaner databaseCleaner;

    User user;
    BoardDto.Response boardResponse;
    ColumnsResponseDto columnsResponse;

    @BeforeEach
    public void beforeEach() {
        databaseCleaner.execute();

        userService.signup(UserDto.SignupRequest.builder().email("sparta@gmail.com").password("testpw11").name("김스파르타").build());
        user = userService.findUserByEmail("sparta@gmail.com");

        boardResponse = boardService.createBoard(user, BoardDto.CreateBoardRequest.builder().name("project").build());
        columnsResponse = columnsService.addColumn(ColumnsRequestDto.AddColumnRequestDto.builder().name("Ready").build(), boardResponse.getId(), user);
    }

    @Nested
    @DisplayName("카드 생성 테스트")
    class create {

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            CardDto.CreateCardRequest request = CardDto.CreateCardRequest.builder()
                    .name("테스트 코드 작성 완료")
                    .build();

            // When
            CardDto.CardResponse response = sut.createCard(user, columnsResponse.getColumnId(), request);

            // Then
            assertEquals(response.getName(), request.getName());
            assertEquals(response.getCreatedBy(), user.getEmail());
        }


        @Test
        @DisplayName("실패 - 없는 컬럼 ID")
        void givenNonExistedColumnId_whenSaving_thenFail() {
            // Given
            CardDto.CreateCardRequest request = CardDto.CreateCardRequest.builder()
                    .name("테스트 코드 작성 완료")
                    .build();

            // When && Then
            assertThatThrownBy(() -> sut.createCard(user, 2L, request))
                    .isInstanceOf(ColumnsCustomException.class)
                    .hasMessage(ColumnsExceptionCode.NOT_FOUND_COLUMNS.getMessage());

        }

        @Test
        @DisplayName("실패 - 보드 작업자가 아닌 경우")
        void givenNonBoardWorkers_whenSaving_thenFail() {
            // Given
            CardDto.CreateCardRequest request = CardDto.CreateCardRequest.builder()
                    .name("테스트 코드 작성 완료")
                    .build();

            userService.signup(UserDto.SignupRequest.builder().email("anotheruser@gmail.com").password("testpw11").name("김낮선").build());
            User anotherUser = userService.findUserByEmail("anotheruser@gmail.com");

            // When & Then
            assertThatThrownBy(() -> sut.createCard(anotherUser, columnsResponse.getColumnId(), request))
                    .isInstanceOf(CardCustomException.class)
                    .hasMessage(CardExceptionCode.FORBIDDEN_ABOUT_REQUEST.getMessage());
        }

    }

    @Nested
    @DisplayName("카드 수정 테스트")
    public class update {

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            CardDto.CardResponse card = createCard();

            CardDto.UpdateCardRequest request = CardDto.UpdateCardRequest.builder()
                    .name("발표용 시연 영상 촬용")
                    .description("오늘 저녁 전까지")
                    .deadLine(LocalDateTime.of(2024, 01, 02, 21, 00, 00))
                    .build();

            // When
            CardDto.CardResponse response = sut.update(user, card.getId(), request);

            // Then
            assertEquals(response.getName(), request.getName());
            assertEquals(response.getDescription(), request.getDescription());
            assertEquals(response.getColor(), card.getColor());

        }

        @Test
        @DisplayName("실패 - 카드 작업자가 아닌 경우")
        void givenNonCardWorkerUser_whenUpdating_thenFail() {
            // Given
            CardDto.CardResponse card = createCard();

            userService.signup(UserDto.SignupRequest.builder().email("anotheruser@gmail.com").password("testpw11").name("김낮선").build());
            User anotherUser = userService.findUserByEmail("anotheruser@gmail.com");

            CardDto.UpdateCardRequest request = CardDto.UpdateCardRequest.builder()
                    .name("발표용 시연 영상 촬용")
                    .description("오늘 저녁 전까지")
                    .deadLine(LocalDateTime.of(2024, 1, 2, 21, 00, 00))
                    .build();

            // When & Then
            assertThatThrownBy(() -> sut.update(anotherUser, card.getId(), request))
                    .isInstanceOf(CardCustomException.class)
                    .hasMessage(CardExceptionCode.FORBIDDEN_ABOUT_CARD.getMessage());

        }
    }

    @Nested
    @DisplayName("카드 삭제 테스트")
    public class delete {

        @Test
        @DisplayName("성공 - 한 개의 카드 만 존재")
        void success() {
            // Given
            CardDto.CardResponse card = createCard();

            // When
            sut.deleteCard(user, card.getId());

            // Then
            assertThatThrownBy(() -> sut.findCardById(card.getId()))
                    .isInstanceOf(CardCustomException.class)
                    .hasMessage(CardExceptionCode.NOT_FOUND_CARD.getMessage());
        }

        @Test
        @DisplayName("성공 - 여러 카드 존재 시, 순서 변경")
        void success_Exist_Cards() {
            // Given
            List<CardDto.CardResponse> cardList = createCardList();

            // When
            sut.deleteCard(user, cardList.get(2).getId());
            List<CardDto.CardResponse> result = sut.getCardsByColumn(user, columnsResponse.getColumnId());

            // Then
            assertFalse(result.contains(cardList.get(2)));
            for (CardDto.CardResponse response : result) {
                System.out.println(response.getSequence());
            }
        }

        @Test
        @DisplayName("실패 - 카드 작업자가 아닌 경우")
        void givenNonCardWorkerUser_whenDeleting_thenFail() {
            // Given
            CardDto.CardResponse card = createCard();

            userService.signup(UserDto.SignupRequest.builder().email("anotheruser@gmail.com").password("testpw11").name("김낮선").build());
            User anotherUser = userService.findUserByEmail("anotheruser@gmail.com");

            // When & Then
            assertThatThrownBy(() -> sut.deleteCard(anotherUser, card.getId()))
                    .isInstanceOf(CardCustomException.class)
                    .hasMessage(CardExceptionCode.FORBIDDEN_ABOUT_CARD.getMessage());
        }

        @Test
        @DisplayName("실패 - 없는 카드 ID 인 경우")
        void givenNonExistedCardId_whenDeleting_thenFail() {
            // Given

            // When & Then
            assertThatThrownBy(() -> sut.deleteCard(user, 1L))
                    .isInstanceOf(CardCustomException.class)
                    .hasMessage(CardExceptionCode.NOT_FOUND_CARD.getMessage());
        }
    }

    @Nested
    @DisplayName("카드 작업자 초대 테스트")
    public class invite_worker {

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            CardDto.CardResponse card = createCard();
            userService.signup(UserDto.SignupRequest.builder().email("anotheruser@gmail.com").password("testpw11").name("김낮선").build());
            User invitedUser = userService.findUserByEmail("anotheruser@gmail.com");
            boardService.inviteNewUser(user, boardResponse.getId(), BoardWorkerDto.BoardWorkerInviteRequest.builder().email(invitedUser.getEmail()).build());
            Card savedCard = sut.findCardById(card.getId());

            // When
            sut.inviteUser(user, card.getId(), CardWorkerDto.CardWorkerInviteRequest.builder().email(invitedUser.getEmail()).build());

            // Then
            assertTrue(cardWorkerService.isExistedWorker(invitedUser, savedCard));
        }

        @Test
        @DisplayName("실패 - 보드 작업자 목록에 없는 경우")
        void givenNonExistedWorker_whenInviting_thenFail() {
            // Given
            CardDto.CardResponse card = createCard();
            userService.signup(UserDto.SignupRequest.builder().email("anotheruser@gmail.com").password("testpw11").name("김낮선").build());
            User anotherUser = userService.findUserByEmail("anotheruser@gmail.com");

            // When & Then
            assertThatThrownBy(() -> sut.inviteUser(user, card.getId(), CardWorkerDto.CardWorkerInviteRequest.builder().email(anotherUser.getEmail()).build()))
                    .isInstanceOf(CardCustomException.class)
                    .hasMessage(CardExceptionCode.BAD_REQUEST_INVITED_REQUEST_USER.getMessage());
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 카드 ID")
        void givenNoExistedCardId_whenInviting_thenFail() {
            // Given

            // When & Then
            assertThatThrownBy(() -> sut.inviteUser(user, 2L, CardWorkerDto.CardWorkerInviteRequest.builder().email("user").build()))
                    .isInstanceOf(CardCustomException.class)
                    .hasMessage(CardExceptionCode.NOT_FOUND_CARD.getMessage());
        }

        @Test
        @DisplayName("실패 - 이미 존재하는 카드 작업자인 경우")
        void givenExistedCardWorker_whenInviting_thenFail() {
            // Given
            CardDto.CardResponse card = createCard();

            // When && Then
            assertThatThrownBy(() -> sut.inviteUser(user, card.getId(), CardWorkerDto.CardWorkerInviteRequest.builder().email(user.getEmail()).build()))
                    .isInstanceOf(CardCustomException.class)
                    .hasMessage(CardExceptionCode.CONFLICT_INVITED_USER.getMessage());
        }
    }

    @Nested
    @DisplayName("동일 컬럼 내에서 카드 순서 변경 테스트")
    public class MoveInSameColumn{

        @Test
        @DisplayName("성공")
        void success() {
            // Given
            List<CardDto.CardResponse> cards = createCardList();

            // When
            CardDto.CardResponse response = sut.moveCardInSameColumn(user, cards.get(2).getId(), CardDto.MoveCardInSameColRequest.builder().sequence(1L).build());
            List<CardDto.CardResponse> cardsByColumn = sut.getCardsByColumn(user, columnsResponse.getColumnId());

            // Then
            assertEquals(response.getSequence(), 1L);
            assertEquals(cardsByColumn.get(0).getId(), response.getId());
            assertEquals(cardsByColumn.get(1).getName(),"card1");
        }

        @Test
        @DisplayName("실패 - 변경할 위치 입력이 잘못된 경우")
        void givenWrongUpdatedSequence_whenMoving_thenFail() {
            // Given


            // When & Then
        }
    }

    private List<CardDto.CardResponse> createCardList() {
        List<CardDto.CreateCardRequest> requests = List.of(
                CardDto.CreateCardRequest.builder().name("card1").build(),
                CardDto.CreateCardRequest.builder().name("card2").build(),
                CardDto.CreateCardRequest.builder().name("card3").build(),
                CardDto.CreateCardRequest.builder().name("card4").build(),
                CardDto.CreateCardRequest.builder().name("card5").build()
        );

        List<CardDto.CardResponse> responses = new ArrayList<>();
        for (CardDto.CreateCardRequest request : requests) {
            responses.add(sut.createCard(user, columnsResponse.getColumnId(), request));
        }
        return responses;
    }

    private CardDto.CardResponse createCard() {
        CardDto.CreateCardRequest request = CardDto.CreateCardRequest.builder()
                .name("테스트 코드 작성 완료")
                .build();

        return sut.createCard(user, columnsResponse.getColumnId(), request);
    }
}