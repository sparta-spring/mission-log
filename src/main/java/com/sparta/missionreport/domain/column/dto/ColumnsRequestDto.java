package com.sparta.missionreport.domain.column.dto;

import com.sparta.missionreport.global.enums.Color;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ColumnsRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "칼럼 추가 요청 dto")
    public static class AddColumnRequestDto {

        @Schema(description = "추가할 이름", defaultValue = "test")
        private String name;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "칼럼 이름 변경 요청 dto")
    public static class UpdateColumnNameRequestDto {

        @Schema(description = "변경할 이름", defaultValue = "test")
        private String name;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "칼럼 색상 변경 요청 dto")
    public static class UpdateColumnColorRequestDto {

        @Schema(description = "변경할 색상", defaultValue = "RED")
        private Color color;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "칼럼 순서 변경 요청 dto")
    public static class UpdateColumnSequenceRequestDto {

        @Schema(description = "변경할 순서", defaultValue = "3")
        private Long sequence;
    }
}
