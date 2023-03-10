package com.sparta.crudbasedjwt.service;

import com.sparta.crudbasedjwt.dto.CommentRequestDto;
import com.sparta.crudbasedjwt.dto.CommentResponseDto;
import com.sparta.crudbasedjwt.dto.PostResponseDto;
import com.sparta.crudbasedjwt.dto.StatusCodeDto;
import com.sparta.crudbasedjwt.entity.Comment;
import com.sparta.crudbasedjwt.entity.Post;
import com.sparta.crudbasedjwt.entity.User;
import com.sparta.crudbasedjwt.entity.UserRoleEnum;
import com.sparta.crudbasedjwt.jwt.JwtUtil;
import com.sparta.crudbasedjwt.repository.CommentRepository;
import com.sparta.crudbasedjwt.repository.PostRepository;
import com.sparta.crudbasedjwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // 댓글 작성
    @Transactional
    public CommentResponseDto addComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 댓글 등록 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) { // JWT의 유효성을 검증하여 올바른 JWT인지 확인??
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);

                // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
                );
                // 게시글의 DB 저장 유무 확인
                Post post = postRepository.findById(postId).orElseThrow(
                        () -> new NullPointerException("일치하는 게시글 없음")
                );
                // 요청 받은 DTO로 DB에 저장할 객체 만들기
                Comment comment = new Comment(commentRequestDto, post, user);
                commentRepository.save(comment);
                return new CommentResponseDto(comment);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
        }
        throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }


    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 등록 가능
        if (token != null && jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);

            // 토큰에서 가져온 사용자 정보를 사용하여 DB조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 댓글에 일치하는 댓글 아이디와 작성자 이름이 있는지 확인
            Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
            );

            comment.setComment(commentRequestDto.getComment());

            return new CommentResponseDto(comment);
        } else {
            throw new IllegalArgumentException("로그인 안함(토큰 없음)");
        }
    }
      // 댓글 삭제
      @Transactional
      public ResponseEntity<StatusCodeDto> deleteComment(Long id, Long commentId, HttpServletRequest request) {
          // Request에서 Token 가져오기
          String token = jwtUtil.resolveToken(request);
          Claims claims;

          // 토큰이 있는 경우에만 게시글 등록 가능
          if (token != null && jwtUtil.validateToken(token)) { // JWT의 유효성을 검증하여 올바른 JWT인지 확인??
              // 토큰에서 사용자 정보 가져오기
              claims = jwtUtil.getUserInfoFromToken(token);

              // 토큰에서 가져온 사용자 정보를 사용하여 DB조회
              User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                      () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
              );

              // 게시글의 DB 저장 유무 확인
              Post post = postRepository.findById(id).orElseThrow(
                      () -> new NullPointerException("일치하는 게시글 없음")
              );

              Comment comment;
              // 유저의 권한이 admin과 같으면 모든 데이터 삭제 가능
              if (user.getRole().equals(UserRoleEnum.ADMIN)) {
                  comment = commentRepository.findById(commentId).orElseThrow(
                          () -> new NullPointerException("일치하는 댓글 없음"));
              } else {
                  comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                          () -> new NullPointerException("작성자만 삭제할 수 있음"));
              }

              commentRepository.delete(comment);

              return ResponseEntity.ok(new StatusCodeDto(HttpStatus.OK.value(),"댓글 삭제 성공"));
          } else {
              throw new IllegalArgumentException("Token Error");
          }
      }

}


//return ResponseEntity.ok(new StatusCodeDto(HttpStatus.BAD_REQUEST.value(),"댓글 삭제 성공"));