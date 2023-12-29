package com.sparta.missionreport.domain.column.dto;

import com.sparta.missionreport.global.enums.Color;
import lombok.Getter;

public class ColumnsRequestDto {

    @Getter
    public static class AddColumnRequestDto {
        private String name;
    }

    @Getter
    public static class UpdateColumnNameRequestDto {
        private String name;
    }

    @Getter
    public static class UpdateColumnColorRequestDto {
        private Color color;
    }
}
