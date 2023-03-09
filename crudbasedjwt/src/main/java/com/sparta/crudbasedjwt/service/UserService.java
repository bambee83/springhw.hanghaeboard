package com.sparta.crudbasedjwt.service;

import com.sparta.crudbasedjwt.dto.LoginRequestDto;
import com.sparta.crudbasedjwt.dto.SignupRequestDto;
import com.sparta.crudbasedjwt.entity.User;
import com.sparta.crudbasedjwt.entity.UserRoleEnum;
import com.sparta.crudbasedjwt.jwt.JwtUtil;
import com.sparta.crudbasedjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service @RequiredArgsConstructor //?? 이거 없으면 repo 랑 연결안되네 ??
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입 구현
    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username); //그냥 찾을때(예외처리 안해줄때) optional 타입으로 !!
        if (found.isPresent()) { //값 있으면 true
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }
        // 사용자 권한 확인
//        UserRoleEnum role = UserRoleEnum.USER;
//        if (signupRequestDto.isAdmin()) { //기본이 false 라서 true 로 들어오면 어드민으로 들어오는거!!
//            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) { //토큰 의존성 주입
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다");
//            }
//            role = UserRoleEnum.ADMIN;
//        }
        User user = new User(username, password);  // 중복사용자 없으면 회원가입시켜줌
        userRepository.save(user); //메소드 종료시 호출한 곳으로 돌아간다!
    }


    //로그인 구현
    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        //사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(  //예외처리 필요할 때는 user 타입으로 !
                () -> new IllegalArgumentException("등록된 사용자가 없습니다")
        );
        //비밀번호 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
    }  //메소드 종료시 호출된 곳으로 돌아간다 !
}
