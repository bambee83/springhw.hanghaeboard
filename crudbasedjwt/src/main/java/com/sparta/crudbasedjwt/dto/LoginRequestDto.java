package com.sparta.crudbasedjwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //세터없으면 서비스단오류남...
public class LoginRequestDto {
    private String username;
    private String password;
}
