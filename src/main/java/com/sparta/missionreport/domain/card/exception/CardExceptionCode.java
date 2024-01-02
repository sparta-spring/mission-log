package com.sparta.missionreport.domain.card.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CardExceptionCode {

    /* 404 - NOT FOUND*/
    NOT_FOUND_CARD(HttpStatus.NOT_FOUND, "CARD-001", "해당 카드를 찾을 수 없습니다."),

    NOT_FOUND_WORKER_IN_CARD(HttpStatus.NOT_FOUND, "CARD-002", "해당 작업자가 존재하지 않습니다."),

    /* 403 - FORBIDDEN*/
    FORBIDDEN_ABOUT_CARD(HttpStatus.FORBIDDEN, "CARD-003", "해당 카드에 대한 권한이 없습니다."),

    FORBIDDEN_ABOUT_REQUEST(HttpStatus.FORBIDDEN, "CARD-004", "해당 요청에 대한 권한이 없습니다."),

    /* 409 - CONFLICT*/
    CONFLICT_INVITED_USER(HttpStatus.CONFLICT, "CARD-005", "이미 초대된 유저 입니다."),

    CONFLICT_UPDATE_SEQUENCE_IS_CURRENT_VALUE(HttpStatus.CONFLICT, "CARD-006", "현재 값과 일치하는 변경 순서 값 입니다."),

    /* 400 - BAD REQUEST*/
    BAD_REQUEST_ABOUT_UPDATE_REQUEST(HttpStatus.BAD_REQUEST, "CARD-007", "유효하지 않는 변경 요청 값 입니다."),

    BAD_REQUEST_INVITED_REQUEST_USER(HttpStatus.BAD_REQUEST, "CARD-008", "해당 유저를 작업자로 초대할 수 없습니다. (보드 작업자가 아님)"),

    BAD_REQUEST_UPDATE_SEQUENCE(HttpStatus.BAD_REQUEST, "CARD-009", "유효하지 않는 변경 순서 값 입니다.");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
