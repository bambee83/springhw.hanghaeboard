package com.sparta.hanghaeboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @NoArgsConstructor
public class SignupRequestDto {
    //@Size: 해당 값이 주어진 값 사이에 해당하는지 검증함(String, Collection, Map, Array 에도 적용 가능)
    //@Pattern: 해당 값이 주어진 패턴과 일치하는지 검증함 //regexp(=regular expression) 정규표현식
    @Size(min = 4, max =10, message = "아이디의 길이는 4에서 10자 사이입니다")
    @Pattern(regexp = "[a-z0-9]*$", message = "아이디 형식이 일치하지 않습니다")
    private String username;
    // 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 로 구성
    // @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+") < 패턴 사용법
    // 패턴 끝에 있는 *를 사용하면 문자열에 지정된 문자와 기호가 0개 이상 포함될 수 있습니다.
    @Size(min = 8,max = 15,message ="비밀번호의 길이는 8자에서 15자 사이입니다")
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$",message = "비밀번호 형식이 일치하지 않습니다")
    private String password;
    //  회원 권한 확인하기 (ADMIN, USER)
    private boolean admin = false;
    private String adminToken = "";

    //관리자 가입토큰 : AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC > boolean true 로 반환
}
