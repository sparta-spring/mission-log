package com.sparta.missionreport.domain.card.exception;

import com.sparta.missionreport.global.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CardCustomException extends CustomException {

    public CardCustomException(CardExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(), exceptionCode.getMessage());

    }
}
