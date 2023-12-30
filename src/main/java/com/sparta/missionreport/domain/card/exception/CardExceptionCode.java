package com.sparta.missionreport.domain.card.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CardExceptionCode {

    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "CARD-001", "해당 카드를 찾을 수 없습니다."),

    NOT_AUTHORIZATION_ABOUT_CARD(HttpStatus.FORBIDDEN, "CARD-002", "해당 카드에 대한 권한이 없습니다."),

    NOT_AUTHORIZATION_ABOUT_REQUEST(HttpStatus.FORBIDDEN, "CARD-003", "해당 요청에 대한 권한이 없습니다."),

    ALREADY_INVITED_USER(HttpStatus.CONFLICT, "CARD-004", "이미 초대된 유저 입니다."),

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "CARD-005", "유효하지 않는 변경 요청 값 입니다."),

    NOT_FOUND_WORKER_IN_CARD(HttpStatus.NOT_FOUND, "CARD-006", "해당 작업자가 존재하지 않습니다."),

    INVALID_UPDATE_SEQUENCE(HttpStatus.BAD_REQUEST, "CARD-007", "유효하지 않는 변경 순서 값 입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
