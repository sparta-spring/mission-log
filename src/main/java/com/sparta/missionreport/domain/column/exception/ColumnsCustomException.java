package com.sparta.missionreport.domain.column.exception;

import com.sparta.missionreport.global.exception.CustomException;
import lombok.Getter;

@Getter
public class ColumnsCustomException extends CustomException {

    public ColumnsCustomException(ColumnsExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}
