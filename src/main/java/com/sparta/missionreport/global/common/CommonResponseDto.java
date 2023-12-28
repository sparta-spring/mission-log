package com.sparta.missionreport.global.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponseDto<T> {

    private Integer statusCode;
    private String message;
    private T data;

}