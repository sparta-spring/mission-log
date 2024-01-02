package com.sparta.missionreport.domain.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentExceptionCode {

    /* 403 - FORBIDDEN*/
    FORBIDDEN_ABOUT_REQUEST(HttpStatus.FORBIDDEN, "COMMENT-001", "해당 요청에 대한 권한이 없습니다."),

    FORBIDDEN_ABOUT_COMMENT(HttpStatus.FORBIDDEN, "COMMENT-002", "해당 댓글에 대한 권한이 없습니다."),

    /* 404 - NOT FOUND*/
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "COMMENT-003", "해당 댓글을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
