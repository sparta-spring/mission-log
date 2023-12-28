package com.sparta.missionreport.domain.column.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ColumnsExceptionCode {

    DUPLICATE_COLUM_NAME(HttpStatus.CONFLICT, "COLUMN-002", "해당 칼럼 이름이 이미 존재합니다."),
    NOT_FOUND_COLUMNS(HttpStatus.NOT_FOUND, "COLUMN-001", "해당 칼럼은 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
