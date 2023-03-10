package com.sparta.crudbasedjwt.entity;

import com.sparta.crudbasedjwt.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// @Getter 필드가져가서 사용
// 기본 생성자 생성해주는 애노테이션
// @Entity 는 course 를 테이블에 매핑하는 jpa 객체로 사용하겠다는 의미 vs @Tostring
@Getter @NoArgsConstructor @Entity
public class Post extends Timestamped { //타임스탬프 상속
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk
    private Long id; // Long 타입

    @Column(nullable = false)  // nullable : null 허용 여부 (false 일때 중복 허용)
    private String username; //추가
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @ManyToOne //연관관계 매핑해주는 이유 > 유저정보받아옴 ! 단방향 어쩌구,,,
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post" ,cascade = CascadeType.REMOVE)// 글 하나가 삭제되면 맵핑되어있는 쪽 테이블이름!!! 글도 삭제되는 cascade 연속성 전이 속성
    @OrderBy("createdAt desc")// 엔티티단에서 정렬
    private List<Comment> commentList = new ArrayList<>();

    //생성자
    public Post(PostRequestDto requestDto, User user) {
        this.username = user.getUsername();  //유저에서 호출.... !!
        this.content = requestDto.getContent();
        this.title = requestDto.getTitle();
//        this.password = requestDto.getPassword();
        this.user = user;
    }

    //매소드 > 게시글 수정 데이터베이스는 소중해요 > 세터남발 x
    public void update(PostRequestDto postRequestDto) {
//        this.username = postRequestDto.getUsername();
        this.content = postRequestDto.getContent();
        this.title = postRequestDto.getTitle();
//        this.password = postRequestDto.getPassword();
    }
}
