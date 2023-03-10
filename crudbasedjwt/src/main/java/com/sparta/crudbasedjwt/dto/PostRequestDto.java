package com.sparta.crudbasedjwt.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PostRequestDto { //id 는 자동으로 생성 !
//    private String username;
    private String title;
    private String content;
}
