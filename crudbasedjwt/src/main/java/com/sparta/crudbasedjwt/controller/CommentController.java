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
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/create/{id}")
    public CommentResponseDto addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request)  {
        return commentService.addComment(id, commentRequestDto, request);}

    // 댓글 수정
    @PutMapping("/update/{id}/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long id, @PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(id, commentId, commentRequestDto,request);}

    // 댓글 삭제
    @DeleteMapping("/delete/{id}/{commentId}")
    public ResponseEntity<StatusCodeDto> deleteComment(@PathVariable Long id,@PathVariable Long commentId, HttpServletRequest request) {
        return commentService.deleteComment(id, commentId, request);}
}

// 생략시 @ModelAttribute @RequestBody 객체로 들어온 바디를 제이슨 형식으로 변환해줍니다