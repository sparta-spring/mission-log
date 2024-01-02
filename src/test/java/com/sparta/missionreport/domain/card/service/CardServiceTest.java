package com.sparta.missionreport.domain.card.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.missionreport.DatabaseCleaner;
import com.sparta.missionreport.domain.board.dto.BoardDto;
import com.sparta.missionreport.domain.board.service.BoardService;
import com.sparta.missionreport.domain.board.service.BoardWorkerService;
import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.exception.CardCustomException;
import com.sparta.missionreport.domain.card.exception.CardExceptionCode;
import com.sparta.missionreport.domain.card.repository.CardRepository;
import com.sparta.missionreport.domain.column.dto.ColumnsRequestDto;
import com.sparta.missionreport.domain.column.dto.ColumnsResponseDto;
import com.sparta.missionreport.domain.column.exception.ColumnsCustomException;
import com.sparta.missionreport.domain.column.exception.ColumnsExceptionCode;
import com.sparta.missionreport.domain.column.service.ColumnsService;
import com.sparta.missionreport.domain.user.dto.UserDto;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        columnsResponse = columnsService.addColumn(ColumnsRequestDto.AddColumnRequestDto.builder().name("Ready").build(), boardResponse.getId());
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
    @DisplayName("카드 수정")
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
                    .deadLine(LocalDateTime.of(2024, 01, 02, 21, 00, 00))
                    .build();

            // When & Then
            assertThatThrownBy(() -> sut.update(anotherUser, card.getId(), request))
                    .isInstanceOf(CardCustomException.class)
                    .hasMessage(CardExceptionCode.FORBIDDEN_ABOUT_CARD.getMessage());

        }
    }

    @Nested
    @DisplayName("삭제")
    public class delete {

        @Test
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
    }

    private CardDto.CardResponse createCard() {
        CardDto.CreateCardRequest request = CardDto.CreateCardRequest.builder()
                .name("테스트 코드 작성 완료")
                .build();

        return sut.createCard(user, columnsResponse.getColumnId(), request);
    }
}