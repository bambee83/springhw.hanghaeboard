package com.sparta.hanghaeboard.dto;

import lombok.Getter;
import lombok.Setter;  //여기서 setter는 왜 필요할까??

@Getter @Setter
public class LoginRequestDto {
    private String username;
    private String password;
}
