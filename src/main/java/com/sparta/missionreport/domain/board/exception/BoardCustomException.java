package com.sparta.missionreport.domain.board.exception;

import com.sparta.missionreport.global.exception.CustomException;

public class BoardCustomException extends CustomException {

    public BoardCustomException(BoardExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}
