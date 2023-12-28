package com.sparta.missionreport.domain.card.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CardExceptionCode {

    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "CARD-001", "해당 카드를 찾을 수 없습니다."),

    NOT_AUTHORIZATION_ABOUT_CARD(HttpStatus.FORBIDDEN, "CARD-002", "해당 카드에 대한 권한이 없습니다."),

    ALREADY_INVITED_USER(HttpStatus.CONFLICT, "CARD-003", "이미 초대된 유저 입니다."),

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "CARD-004", "유효하지 않는 변경 요청 값 입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}