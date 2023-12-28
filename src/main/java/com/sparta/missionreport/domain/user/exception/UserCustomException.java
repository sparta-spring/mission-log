package com.sparta.missionreport.domain.user.exception;

import com.sparta.missionreport.global.exception.CustomException;
import lombok.Getter;

@Getter
public class UserCustomException extends CustomException {

    public UserCustomException(UserExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}
