package com.sparta.missionreport.global.data;

import com.sparta.missionreport.domain.column.dto.ColumnsRequestDto;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.global.enums.Color;

public interface ColumnsData {
    String TEST_COLUMN_NAME = "test";
    Long TEST_COLUMN_SEQUENCE = 1L;
    Color TEST_COLUMN_COLOR = Color.NONE;

    String TEST_ANOTHER_COLUMN_NAME = "AnotherTest";
    Long TEST_ANOTHER_COLUMN_SEQUENCE = 1L;
    Color TEST_ANOTHER_COLUMN_COLOR = Color.ORANGE;

    ColumnsRequestDto.AddColumnRequestDto columnRequestDto = ColumnsRequestDto.AddColumnRequestDto
            .builder()
            .name(TEST_COLUMN_NAME)
            .build();

    ColumnsRequestDto.UpdateColumnNameRequestDto columnNameRequestDto = ColumnsRequestDto.UpdateColumnNameRequestDto
            .builder()
            .name(TEST_COLUMN_NAME)
            .build();

    ColumnsRequestDto.UpdateColumnColorRequestDto columnColorRequestDto = ColumnsRequestDto.UpdateColumnColorRequestDto
            .builder()
            .color(TEST_COLUMN_COLOR)
            .build();

    ColumnsRequestDto.UpdateColumnSequenceRequestDto sequenceRequestDto = ColumnsRequestDto.UpdateColumnSequenceRequestDto
            .builder()
            .sequence(TEST_COLUMN_SEQUENCE)
            .build();

    Columns TEST_COLUMNS = Columns.builder()
            .name(TEST_COLUMN_NAME)
            .color(TEST_COLUMN_COLOR)
            .build();

    Columns TEST_ANOTHER_COLUMNS = Columns.builder()
            .name(TEST_ANOTHER_COLUMN_NAME)
            .color(TEST_ANOTHER_COLUMN_COLOR)
            .build();
}
