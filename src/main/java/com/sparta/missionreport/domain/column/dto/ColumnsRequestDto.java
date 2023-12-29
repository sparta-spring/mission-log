package com.sparta.missionreport.domain.column.dto;

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
}
