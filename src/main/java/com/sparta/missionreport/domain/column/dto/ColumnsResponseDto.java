package com.sparta.missionreport.domain.column.dto;

import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.global.enums.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnsResponseDto {
    private Long boardId;
    private String name;
    private Color color;
    private Long sequence;

    public ColumnsResponseDto(Columns columns) {
        this.boardId = columns.getBoard().getId();
        this.name = columns.getName();
        this.color = columns.getColor();
        this.sequence = columns.getSequence();
    }
}
