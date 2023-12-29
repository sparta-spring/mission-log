package com.sparta.missionreport.domain.checklist.exception;

import com.sparta.missionreport.global.exception.CustomException;
import lombok.Getter;

@Getter
public class ChecklistCustomException extends CustomException {

    public ChecklistCustomException(ChecklistExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}
