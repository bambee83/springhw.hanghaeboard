package com.sparta.crudbasedjwt.service;

import com.sparta.crudbasedjwt.dto.PostListResponseDto;
import com.sparta.crudbasedjwt.dto.PostRequestDto;
import com.sparta.crudbasedjwt.dto.PostResponseDto;
import com.sparta.crudbasedjwt.dto.StatusCodeDto;
import com.sparta.crudbasedjwt.entity.Post;
import com.sparta.crudbasedjwt.entity.User;
import com.sparta.crudbasedjwt.jwt.JwtUtil;
import com.sparta.crudbasedjwt.repository.PostRepository;
import com.sparta.crudbasedjwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j @Service @RequiredArgsConstructor // 이거 없으면 의존성주입 안됨
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

//  중복코드제거 > 정리필요


    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
    // Request에서 Token 가져오기
    String token = jwtUtil.resolveToken(request);
    Claims claims;

    // 토큰이 있는 경우에만 게시글 등록 가능
    if (token != null) {
        if (jwtUtil.validateToken(token)) { // JWT의 유효성을 검증하여 올바른 JWT인지 확인??
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        // 요청받은 DTO로 DB에 저장할 객체 만들기, 토큰에 있는 작성자 이름을 같이 넣음
        Post post = postRepository.saveAndFlush(new Post(postRequestDto, user));
        return new PostResponseDto(post);
    }
    throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }
    //전체 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostListResponseDto> getPostList() {
        // PostResponseDto 객체만 들어올 수 있는 리스트 생성
        List<PostListResponseDto> postResponseDtoList = new ArrayList<>();
        // 데이터 베이스에서 찾은 모든값을 리스트로 저장
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        for (Post post : postList) { // 리스트에서 하나씩 꺼내서 postResponseDtoList 리스트에 저장
            postResponseDtoList.add(new PostListResponseDto(post));
//            log.info("post = {}", post);
        }
        return postResponseDtoList;
    }
    //선택 게시글 조회
    @Transactional(readOnly = true)
    public PostListResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("일치하는 게시글 없음")
        );
        return new PostListResponseDto(post);
    }
    //선택 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 수정가능
        if (token != null) {
            // JWT의 유효성을 검증하여 올바른 토큰인지 확인
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 게시글에 일치하는 게시글 아이디와 작성자 이름이 있는지 확인
            Post post = postRepository.findByIdAndUsername(id, claims.getSubject()).orElseThrow(
                    () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
            );

            post.update(postRequestDto);  // 공부 필요 또는 세터쓰는 방법 (몇가지만 넘겨줄떄...? )
            return new PostResponseDto(post);
        }
        throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }
    //선택 게시글 삭제
    @Transactional
    public StatusCodeDto deletePost(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 수정가능
        if (token != null) {
            // JWT의 유효성을 검증하여 올바른 토큰인지 확인
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.findByIdAndUsername(id, claims.getSubject()).orElseThrow(
                    () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
            );

            postRepository.deleteById(id);
            return new  StatusCodeDto(200,"게시글 삭제 성공");
        }
        throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }
}
