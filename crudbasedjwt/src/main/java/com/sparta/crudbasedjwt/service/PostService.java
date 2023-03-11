package com.sparta.crudbasedjwt.service;

import com.sparta.crudbasedjwt.dto.*;
import com.sparta.crudbasedjwt.entity.Comment;
import com.sparta.crudbasedjwt.entity.Post;
import com.sparta.crudbasedjwt.entity.User;
import com.sparta.crudbasedjwt.entity.UserRoleEnum;
import com.sparta.crudbasedjwt.jwt.JwtUtil;
import com.sparta.crudbasedjwt.repository.PostRepository;
import com.sparta.crudbasedjwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j @Service @RequiredArgsConstructor //final 에 붙은 친구들 생성자로 주입해줌 @Bean 주입
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

//  중복코드제거 > 정리필요


    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        // Request 에서 Token 가져오기
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
            Post post = postRepository.saveAndFlush(new Post(postRequestDto, user));  //영속성컨텍스트에 담겼다가 저장이 아닌 바로 데이터베이스에 인서트문을 날린다 !
            return new PostResponseDto(post);
        }
        throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }

    //전체 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDto = new ArrayList<>();
        for (Post post : postList) {
            List<CommentResponseDto> commentList = new ArrayList<>(); //Post안에 코멘트 리스트 넣어서 순환참조 방지하기
            for (Comment comment : post.getCommentList()) {
                commentList.add(new CommentResponseDto(comment));
            }
            PostResponseDto postDto = new PostResponseDto(post, commentList);
            postResponseDto.add(postDto);
        }
        return postResponseDto;
    }


    //선택 게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("일치하는 게시글 없음")
        );
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : post.getCommentList()) {
            commentList.add(new CommentResponseDto(comment));
        }
        return new PostResponseDto(post, commentList);
    }

    //선택 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        //Request 에서 토큰 가져오기
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
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
            Post post;
            //유저의 권한이 admin과 같으면 모든 데이터 수정 가능
            if (user.getRole().equals(UserRoleEnum.ADMIN)) {
                post = postRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
            } else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 수정 가능
                post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new IllegalArgumentException("작성자만 수정할 수 있습니다."));
            }
            post.update(postRequestDto);  // 공부 필요 또는 세터쓰는 방법 (몇가지만 넘겨줄떄...? )
            return new PostResponseDto(post);
        }
        throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }
    //선택 게시글 삭제
    @Transactional
    public ResponseEntity<StatusCodeDto> deletePost(Long id, HttpServletRequest request) {
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
            Post post;
            //유저의 권한이 admin과 같으면 모든 데이터 삭제 가능
            if (user.getRole().equals(UserRoleEnum.ADMIN)) {
                post = postRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
            } else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 삭제 가능
                post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new IllegalArgumentException("작성자만 삭제할 수 있습니다."));
            }
            postRepository.deleteById(id);
            return ResponseEntity.ok(new StatusCodeDto(HttpStatus.OK.value(), "게시글 삭제 성공"));
        }
        throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }

}



