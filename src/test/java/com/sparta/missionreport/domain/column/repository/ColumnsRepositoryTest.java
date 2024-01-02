package com.sparta.missionreport.domain.column.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.repository.BoardRepository;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.column.util.ColumnsTestUtil;
import com.sparta.missionreport.global.data.BoardData;
import com.sparta.missionreport.global.data.ColumnsData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ColumnsRepositoryTest implements BoardData, ColumnsData {

    @Autowired
    ColumnsRepository columnsRepository;

    @Autowired
    BoardRepository boardRepository;

    @MockBean
    JPAQueryFactory jpaQueryFactory;

    Board board;
    Board anotherBoard;

    @BeforeEach
    void setUp() {
        board = boardRepository.save(TEST_BOARD);
        anotherBoard = boardRepository.save(TEST_ANOTHER_BOARD);
    }

    @Test
    @DisplayName("")
     void findTopByBoardIdAndIsDeletedFalseOrderBySequenceDesc() {
        // given
        Columns column1 = ColumnsTestUtil.get(TEST_COLUMNS
                , 1L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 1L
                , false);

        Columns column2 = ColumnsTestUtil.get(TEST_COLUMNS
                , 2L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 2L
                , false);

        Columns column3 = ColumnsTestUtil.get(TEST_COLUMNS
                , 3L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 3L
                , false);

        columnsRepository.save(column1);
        columnsRepository.save(column2);
        columnsRepository.save(column3);

        // when
        Columns resultColumns = columnsRepository.findTopByBoardIdAndIsDeletedFalseOrderBySequenceDesc(board.getId()).orElse(null);
        List<Board> boardList = boardRepository.findAll();
        for(Board board : boardList){
            System.out.println("Board ID : " + board.getId());
        }
        // then
        assertThat(Objects.requireNonNull(resultColumns).getId()).isEqualTo(column3.getId());
    }

    @Test
    void findAllByIsDeletedFalseAndBoardIdAndSequenceBetween() {
        // given
        Columns column1 = ColumnsTestUtil.get(TEST_COLUMNS
                , 1L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 1L
                , false);

        Columns column2 = ColumnsTestUtil.get(TEST_COLUMNS
                , 2L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 2L
                , true);

        Columns column3 = ColumnsTestUtil.get(TEST_COLUMNS
                , 3L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_ANOTHER_BOARD
                , 3L
                , false);

        Columns column4 = ColumnsTestUtil.get(TEST_COLUMNS
                , 4L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 4L
                , true);

        Columns column5 = ColumnsTestUtil.get(TEST_COLUMNS
                , 5L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 5L
                , false);

        Columns column6 = ColumnsTestUtil.get(TEST_COLUMNS
                , 6L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 6L
                , false);

        columnsRepository.save(column1);
        columnsRepository.save(column2);
        columnsRepository.save(column3);
        columnsRepository.save(column4);
        columnsRepository.save(column5);
        columnsRepository.save(column6);

        // when
        List<Columns> columnsList = columnsRepository.findAllByIsDeletedFalseAndBoardIdAndSequenceBetween(board.getId(), column1.getSequence(), column6.getSequence());
        List<Board> boardList = boardRepository.findAll();
        for(Board board : boardList){
            System.out.println("Board ID : " + board.getId());
        }
        // then
        assertThat(columnsList.get(0).getId()).isEqualTo(column1.getId());
        assertThat(columnsList.get(1).getId()).isEqualTo(column5.getId());
        assertThat(columnsList.get(2).getId()).isEqualTo(column6.getId());
    }

    @Test
    void findAllByBoardIdAndIsDeletedFalseAndSequenceGreaterThan() {
        // given
        Columns column1 = ColumnsTestUtil.get(TEST_COLUMNS
                , 1L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 1L
                , false);

        Columns column2 = ColumnsTestUtil.get(TEST_COLUMNS
                , 2L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 2L
                , true);

        Columns column3 = ColumnsTestUtil.get(TEST_COLUMNS
                , 3L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_ANOTHER_BOARD
                , 3L
                , false);

        Columns column4 = ColumnsTestUtil.get(TEST_COLUMNS
                , 4L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 4L
                , true);

        Columns column5 = ColumnsTestUtil.get(TEST_COLUMNS
                , 5L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 5L
                , false);

        Columns column6 = ColumnsTestUtil.get(TEST_COLUMNS
                , 6L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 6L
                , false);

        columnsRepository.save(column1);
        columnsRepository.save(column2);
        columnsRepository.save(column3);
        columnsRepository.save(column4);
        columnsRepository.save(column5);
        columnsRepository.save(column6);

        // when
        List<Columns> columnsList = columnsRepository.findAllByBoardIdAndIsDeletedFalseAndSequenceGreaterThan(board.getId(), 4L);
        List<Board> boardList = boardRepository.findAll();
        for(Board board : boardList){
            System.out.println("Board ID : " + board.getId());
        }
        // then
        assertThat(columnsList.get(0).getId()).isEqualTo(column5.getId());
        assertThat(columnsList.get(1).getId()).isEqualTo(column6.getId());

    }

    @Test
    void findByNameAndIsDeletedFalseAndBoardId() {
        // given
        Columns column1 = ColumnsTestUtil.get(TEST_COLUMNS
                , 1L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 1L
                , false);

        Columns column2 = ColumnsTestUtil.get(TEST_ANOTHER_COLUMNS
                , 2L
                , LocalDateTime.now().minusMinutes(2)
                , LocalDateTime.now().minusMinutes(2)
                , TEST_BOARD
                , 2L
                , false);

        columnsRepository.save(column1);
        columnsRepository.save(column2);

        // when
        Columns columnsResult = columnsRepository.findByNameAndIsDeletedFalseAndBoardId(TEST_COLUMN_NAME, board.getId()).orElse(null);
        List<Board> boardList = boardRepository.findAll();
        for(Board board : boardList){
            System.out.println("Board ID : " + board.getId());
        }
        // then
        assertThat(Objects.requireNonNull(columnsResult).getId()).isEqualTo(column1.getId());

    }
}