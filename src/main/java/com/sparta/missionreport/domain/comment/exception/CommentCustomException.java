package com.sparta.missionreport.domain.comment.exception;

import com.sparta.missionreport.domain.card.exception.CardExceptionCode;
import com.sparta.missionreport.global.exception.CustomException;
import lombok.Getter;

@Getter
public class CommentCustomException extends CustomException {

    public CommentCustomException(CommentExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(), exceptionCode.getMessage());

    }
}
