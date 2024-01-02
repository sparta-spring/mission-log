package com.sparta.missionreport.global.data;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.enums.Color;

public interface BoardData {
    String ANOTHER_PREFIX = "another-";
    Long TEST_BOARD_ID = 1L;
    Long TEST_ANOTHER_BOARD_ID = 2L;
    Color TEST_BOARD_COLOR = Color.NONE;
    String TEST_BOARD_NAME = "test";

    String TEST_BOARD_DESC = "test test";

    Board TEST_BOARD = Board.builder()
            .color(TEST_BOARD_COLOR)
            .name(TEST_BOARD_NAME)
            .description(TEST_BOARD_DESC)
            .build();

    Board TEST_ANOTHER_BOARD = Board.builder()
            .color(TEST_BOARD_COLOR)
            .name(ANOTHER_PREFIX + TEST_BOARD_NAME)
            .description(ANOTHER_PREFIX + TEST_BOARD_DESC)
            .build();

}
