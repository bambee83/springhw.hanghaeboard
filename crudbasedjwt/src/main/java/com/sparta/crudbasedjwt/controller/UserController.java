package com.sparta.crudbasedjwt.controller;

import com.sparta.crudbasedjwt.dto.LoginRequestDto;
import com.sparta.crudbasedjwt.dto.SignupRequestDto;
import com.sparta.crudbasedjwt.dto.StatusCodeDto;
import com.sparta.crudbasedjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController @RequiredArgsConstructor // 이거없으면 의존성 주입안됨
@RequestMapping("/post/user")
public class UserController {
    private final UserService userService;
    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<StatusCodeDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) { //@Valid 가 dto 조건 ck
        userService.signup(signupRequestDto);
        return ResponseEntity.ok(new StatusCodeDto(HttpStatus.OK.value(),"회원가입성공"));
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<StatusCodeDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok(new StatusCodeDto(200,"로그인성공"));  //서비스단 말고 컨트롤러 단에서 엔티티객체사용 반환함
                                                                                            // 바디타입은 <statuscodedto> 대충.. 이해는감...
    }
}
