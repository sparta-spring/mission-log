package com.sparta.missionreport.domain.column.dto;

import lombok.Getter;

public class ColumnsRequestDto {

    @Getter
    public static class AddColumnsRequestDto {
        private String name;
    }
}
