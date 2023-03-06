package com.sparta.hanghaeboard.controller;
import com.sparta.hanghaeboard.dto.LoginRequestDto;
import com.sparta.hanghaeboard.dto.SignupRequestDto;
import com.sparta.hanghaeboard.dto.StatusCodeDto;
import com.sparta.hanghaeboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller  //@RestController ?  > template 의 html 만 반환해도 될때는 @controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService; //(의존성주입) Model 계층의 service 를 이용하기 위한 선언

    //회원가입
//    @PostMapping("/signup")
//    public String signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
////        return userService.signup(signupRequestDto);
//        return null;
//    }
    @PostMapping("/signup")
    public ResponseEntity<StatusCodeDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.ok(new StatusCodeDto( HttpStatus.OK.value(),"회원가입 성공"));
    }

    //로그인
//    @ResponseBody
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequestDto loginRequestDto) {
//        userService.login(loginRequestDto);
//        return null;
//    }
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<StatusCodeDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok(new StatusCodeDto(HttpStatus.OK.value(),"로그인 성공"));
    }


}