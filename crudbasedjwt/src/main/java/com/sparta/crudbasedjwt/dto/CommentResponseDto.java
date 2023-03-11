package com.sparta.crudbasedjwt.dto;

import com.sparta.crudbasedjwt.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long postId;
    private String username;
    private String comment;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {  //서비스단에서 댓글저장할때 매개변수없다고 나옴...
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this. modifiedAt = comment.getModifiedAt();
    }
}
