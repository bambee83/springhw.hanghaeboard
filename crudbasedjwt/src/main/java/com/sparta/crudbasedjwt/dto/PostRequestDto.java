package com.sparta.crudbasedjwt.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PostRequestDto { //id 는 자동으로 생성 !   > 클라에서 요청하는 거
//    private String username;
    private String title;
    private String content;
}

//@Tostring ??  ToString() 메소드 자동생성 (롬복패키지)