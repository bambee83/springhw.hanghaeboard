package com.sparta.crudbasedjwt.controller;


import com.sparta.crudbasedjwt.dto.CommentRequestDto;
import com.sparta.crudbasedjwt.dto.CommentResponseDto;
import com.sparta.crudbasedjwt.dto.StatusCodeDto;
import com.sparta.crudbasedjwt.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")  //api 수정
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/create/{id}")  //postId 로 수정 또는 아예 POST: /comments (id 는  request 안에 들어있음 ) , 이 아이디는 포스트 아이디 입니다. 
    public CommentResponseDto addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request)  {
        return commentService.addComment(id, commentRequestDto, request);}

    // 댓글 수정
    @PutMapping("/update/{id}")  //PUT: 이 아이디는 코멘트의 아이디 입니다.
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(id,commentRequestDto,request);}

    // 댓글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StatusCodeDto> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        return commentService.deleteComment(id,  request);}
}

// 생략시 @ModelAttribute @RequestBody 객체로 들어온 바디를 제이슨 형식으로 변환해줍니다