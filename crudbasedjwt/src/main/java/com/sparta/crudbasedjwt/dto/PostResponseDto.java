package com.sparta.crudbasedjwt.dto;

import com.sparta.crudbasedjwt.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto { //pw 제외 나머지 필드생성
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;


    public PostResponseDto(Post post) { //생성자 ...서비스단 짜다가 만든다 ! 반환할때 2가지 방법 세터 or 생성자초기ghkgotj 객체생성
        this.id = post.getId();
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }
}