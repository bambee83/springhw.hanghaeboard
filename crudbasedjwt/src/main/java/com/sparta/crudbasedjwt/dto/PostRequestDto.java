package com.sparta.crudbasedjwt.dto;

import lombok.Getter;


@Getter
public class PostRequestDto { //id 는 자동으로 생성 !
    private String username;
    private String title;
    private String contents;
}
