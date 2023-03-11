package com.sparta.crudbasedjwt.entity;

import com.sparta.crudbasedjwt.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @NoArgsConstructor @Setter
public class Comment extends Timestamped {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)   //연관관계 매핑 : 단방향 , 다대일(여려개의코멘트에 한개의포스트)
    @JoinColumn(name = "POST_ID", nullable = false) // 왜래키매핑, 주인은 포스트임 , null 허용x
    private Post post;

    @ManyToOne  //연관관계 매핑
    @JoinColumn(name ="user_id") //불필요한 쿼리를 줄여야 한다... stateless 하지않음 !
    private User user;


    public Comment(CommentRequestDto commentRequestDto, Post post, User user) {
        this.comment = commentRequestDto.getComment();
        this.username = user.getUsername();
        this.post = post;
        this.user = user;
    }
    public void update(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }

}
