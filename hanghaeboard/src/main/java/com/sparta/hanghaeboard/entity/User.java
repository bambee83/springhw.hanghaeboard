package com.sparta.hanghaeboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @NoArgsConstructor @Entity(name="users") // 예약어 User
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable : null 허용 여부
    // unique : 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable=false, unique=true)
    private String username;
    @Column(nullable =false)
    private String password;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) //jpa 심화참고
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) { //생성자를 통한 초기화
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
