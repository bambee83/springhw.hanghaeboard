package com.sparta.hanghaeboard.dto;

// Dto : Controller > Model 계층으로 data 전송
import lombok.Getter;

@Getter
public class PostRequestDto { //id는 자동으로 생성된다 ! (id 제외 나머지 요청값 필드주입)
    private String username;
    private String content;
    private String password;
    private String title;

}
// 얘는 왜 안만들어 주죠 ...?
//public PostRequestDto (String username, String content, String password, String title){
//    this.username = username;
//    this.content = content;
//    this.password = password;
//    this.title = title;
//}


//postman 과 h2콘솔에서 찍히는 거랑 순서가 다름...어떻게 다른지 알아보자 !  왜 달라질까용...?!
//response 순서로 출력된다 (포스트맨) > 메모지 참고 !!
