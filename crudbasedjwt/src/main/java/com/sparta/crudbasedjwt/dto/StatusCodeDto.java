package com.sparta.crudbasedjwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor //기본생성자 없으면 오류남
public class StatusCodeDto {
    private int statusCode;   //인트타입입니다...!
    private String msg;

    public StatusCodeDto(int statusCode, String msg) {  //매개변수 있는 생성자 (controller 단에서 필요)
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
