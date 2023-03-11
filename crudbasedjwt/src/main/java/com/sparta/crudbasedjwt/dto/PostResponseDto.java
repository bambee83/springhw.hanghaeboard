package com.sparta.crudbasedjwt.dto;

import com.sparta.crudbasedjwt.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
public class PostResponseDto { //pw 제외 나머지 필드생성  > 클라에 주는 거 (응답)
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();


    public PostResponseDto(Post post) { //생성자 ...서비스단 짜다가 만든다 ! 반환할때 2가지 방법 세터 or 생성자초기화 객체생성
        this.id = post.getId();
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    public PostResponseDto(Post post, List<CommentResponseDto> commentList) { //오버로딩
        this.id = post.getId();
        this.username = post.getUsername();
        this.content = post.getContent();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = commentList;
    }

}