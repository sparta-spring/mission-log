package com.sparta.missionreport.domain.card.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CardExceptionCode {

    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "CARD-001", "해당 카드를 찾을 수 없습니다."),

    NOT_AUTHORIZATION_UPDATABLE_CARD(HttpStatus.FORBIDDEN, "CARD-002", "해당 카드를 수정할 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
