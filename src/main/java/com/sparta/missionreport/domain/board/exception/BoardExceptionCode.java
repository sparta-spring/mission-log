package com.sparta.missionreport.domain.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardExceptionCode {

    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "BOARD-001", "해당 보드는 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
