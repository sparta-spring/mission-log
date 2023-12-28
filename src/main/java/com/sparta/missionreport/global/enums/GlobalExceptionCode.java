package com.sparta.missionreport.global.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum GlobalExceptionCode {

    INVALID_VALUE(BAD_REQUEST, "값이 유효하지 않습니다."),
    INVALID_PARAMETER(BAD_REQUEST, "파라미터가 누락되었습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러 입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
