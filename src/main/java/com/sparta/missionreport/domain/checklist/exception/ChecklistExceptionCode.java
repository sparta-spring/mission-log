package com.sparta.missionreport.domain.checklist.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChecklistExceptionCode {

    // BAD_REQUEST 400
    BAD_REQUEST_INVALID_UPDATE_SEQUENCE(HttpStatus.BAD_REQUEST, "CHECKLIST-001",
            "유효하지 않는 변경 순서 값 입니다."),

    // UNAUTHORIZED 401

    // FORRBIDDEN 403
    FORBIDDEN_YOUR_NOT_COME_IN_CHECKLIST(HttpStatus.FORBIDDEN, "CHECKLIST-002",
            "체크리스트의 접근 권한이 없습니다"),

    // NOT_FOUND 404
    NOT_FOUND_CHECKLIST(HttpStatus.NOT_FOUND, "CHECKLIST-003", "해당 체크리스트는 존재하지 않습니다.");

    // CONFLICT 409

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}