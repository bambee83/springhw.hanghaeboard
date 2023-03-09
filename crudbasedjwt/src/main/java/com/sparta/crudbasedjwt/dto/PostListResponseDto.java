package com.sparta.crudbasedjwt.dto;

import com.sparta.crudbasedjwt.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter //관리자는 모든리스트.....
public class PostListResponseDto {
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    public PostListResponseDto(Post post) {
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }
}