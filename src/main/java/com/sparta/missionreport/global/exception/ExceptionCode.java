package com.sparta.missionreport.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    // BAD_REQUEST 400
    BAD_REQUEST_NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),

    // UNAUTHORIZED 401
    UNAUTHORIZED_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰의 유효기간이 만료 되었습니다"),

    UNAUTHORIZED_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 입니다."),

    UNAUTHORIZED_TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "지원되지 않는 토큰 입니다"),

    UNAUTHORIZED_TOKEN_INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "토큰의 서명이 유효하지 않습니다."),

    UNAUTHORIZED_TOKEN_EMPTY_CLAIMS(HttpStatus.UNAUTHORIZED, "잘못된 토큰 입니다"),

    UNAUTHORIZED_TOKEN_GENERIC_ERROR(HttpStatus.UNAUTHORIZED, "토큰 처리 중 오류가 발생했습니다"),

    UNAUTHORIZED_TOKEN_IS_NULL(HttpStatus.UNAUTHORIZED, "토큰이 없습니다"),

    // FORRBIDDEN 403
    FORBIDDEN_YOUR_NOT_COME_IN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다"),

    // NOT_FOUND 404
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저는 존재하지 않습니다."),

    // CONFLICT 409
    CONFLICT_USER_EMAIL_IN_USE(HttpStatus.CONFLICT, "사용자 이메일이 이미 사용 중 입니다");

    private final HttpStatus httpStatus;
    private final String message;

}