package com.sparta.crudbasedjwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter @NoArgsConstructor //세터없으면 안됨 왜??
public class SignupRequestDto {
    @NotNull // null 처리 잊지말자 !! 근데 여기서 안해주고 서비스단에서 해주면 안되나???
    @Size(min=4, max=10, message = "아이디는 4에서 10자 사이입니다")
    @Pattern(regexp = "^[a-z0-9]*$", message = "아이디는 알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다")
    private String username;

    @NotNull
    @Size(min=8, max=15, message = "비밀번호는 8에서 15자 사이입니다")
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$",message = "비밀번호 형식이 일치하지 않습니다")
    private String password;

   // 회원 권한 확인하기 (ADMIN, USER)
    private boolean admin = false;
    private String adminToken = "";
}

//관리자 가입토큰 : AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC > boolean true 로 반환
// @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")  > 영미님 방법
