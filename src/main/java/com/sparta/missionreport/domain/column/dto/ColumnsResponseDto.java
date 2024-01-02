package com.sparta.missionreport.domain.column.dto;

import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.global.enums.Color;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnsResponseDto {

    @Schema(description = "보드 아이디", nullable = false, example = "1L")
    private Long boardId;
    @Schema(description = "칼럼 이름", nullable = false, example = "test")
    private String name;
    @Schema(description = "칼럼 색상", nullable = false, example = "NONE")
    private Color color;
    @Schema(description = "칼럼 순서", nullable = false, example = "2L")
    private Long sequence;


    public ColumnsResponseDto(Columns columns) {
        this.boardId = columns.getBoard().getId();
        this.name = columns.getName();
        this.color = columns.getColor();
        this.sequence = columns.getSequence();
    }
}
