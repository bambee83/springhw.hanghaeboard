package com.sparta.crudbasedjwt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter @NoArgsConstructor @Entity(name="users") //예약어 user 변경 !
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부 (true 일때 null 허용)
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value=EnumType.STRING) // 이넘을 테이블에서 사용하겠다 !! / 일반적으로 스트링사용 (cf. orginal)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE) // 이게 뭔지는 모르지만 없으면 안된다...
    private List<Post> post;

    //생성자를 통한 초기화
    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
