package com.sparta.hanghaeboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusCodeDto {

    private int statusCode;
    private String msg;

    public StatusCodeDto(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}