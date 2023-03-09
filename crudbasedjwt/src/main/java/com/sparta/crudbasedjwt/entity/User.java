package com.sparta.crudbasedjwt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
//    @Column(nullable = false)
//    @Enumerated(value=EnumType.STRING) //Jpa 심화강의 참고 (?????)
//    private UserRoleEnum role;

    //생성자를 통한 초기화 .. 문득 @AllArgs 나 required 를 넣어주면 이거 없어도 되지 않나...
    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        this.role = role;
    }
}
