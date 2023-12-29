package com.sparta.missionreport.domain.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardExceptionCode {

    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "BOARD-001", "해당 보드는 존재하지 않습니다."),

    NOT_AUTHORIZATION_ABOUT_BOARD(HttpStatus.FORBIDDEN, "BOARD-002","해당 보드에 대한 권한이 없습니다."),

    ALREADY_INVITED_USER(HttpStatus.CONFLICT, "BOARD-003", "이미 초대된 유저 입니다."),

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "BOARD-004", "유효하지 않는 변경 요청 값 입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
