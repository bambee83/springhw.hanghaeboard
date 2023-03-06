package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.PostRequestDto;
import com.sparta.hanghaeboard.dto.PostResponseDto;
import com.sparta.hanghaeboard.entity.Post;
import com.sparta.hanghaeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service // 스프링이 Model계층의 서비스역할을 하는 객체로 인식하기 위함
@RequiredArgsConstructor // 생성자 자동생성
public class PostService {
// 모델 계층
// User (@Entity) : 데이터베이스와 통신한다.
// UserRepository : 데이터베이스의 테이블 역할을 한다.
// 핵심 비즈니스 로직
//      1. 테이블에 값을 세팅한다 (User 객체에 값을 세팅한다)
    //      1-1. 사용자가 보낸 값을 꺼낸다. String email = userRequestDto.getEmail();
    //      1-2. User 객체에 값을 셋팅한다. User user = new User();
    //                                user.setEmail(email);
//      2. 값을 세팅한 user 객체를 데이터베이스에 저장한다.
    //      2-1. 유저 레포지토리가 데이터베이스에 유저를 저장한다. .save()
    private final PostRepository postRepository;  //의존성 주입 (인접계층인 repository 객체와 소통)

    // 인접한 계층인 repository 계층으로 데이터를 전달하는 중
    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        return new PostResponseDto(postRepository.save(post));}

    @Transactional(readOnly = true)
    public List<PostResponseDto> getListPosts() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDto = new ArrayList<>();
        for (Post post : postList) {  //향상된 for 문
            PostResponseDto postDto = new PostResponseDto(post);
            postResponseDto.add(postDto);}
        return postResponseDto;}

//     return postList.stream().map(post -> new PostResponseDto(post)); // 향상된 for 문을 이런식으로 stream 으로 한줄 가능

    // 향상된 for 문 : for(자료형 (한단계아래의자료형의)변수명 : 배열명) {문장}
    // 인덱스를 사용하지 못한다 , 배열이나 ArrayList 값 을 사용할 순 있지만 절대 수정할 수는 없다.
    // 1.간편한,가독성 좋은 코드
    //2. 배열 인덱스 문제 해결 (ArrayIndexOutOfBoundsException 예외를 피할 수 있다
    // 배열처럼 여러 원소로 이루어진 집합의 모든 원소에 대해 특정 작업을 반복하기 위해 사용
    // 자바의 Arraylist 컬렉션 참고

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));  // 이것이 람다식이다 ! (익명함수)
        return new PostResponseDto(post);}

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        PostResponseDto postResponseDto = new PostResponseDto(post);

        if (requestDto.getPassword().equals(post.getPassword())) {
            post.update(requestDto);
            return postResponseDto;}
        else {return postResponseDto;}
    }

    @Transactional
    public Map<String, Object> deletePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        Map<String, Object> reponse = new HashMap<>(); //뒤에서배우는.....컬랙션....해쉬맵...
        if (requestDto.getPassword().equals(post.getPassword())) {
            postRepository.deleteById(id);
            reponse.put("success",true);
            return reponse;} else {
            reponse.put("success",false);
            return reponse;} }
}



// 예외처리가 중요합니다, 사용자가 우리가 원하는 대로 요청하지 않을 수 있다 ex. 제목이나 페스워드 가 null값일때
// 유효성검사 검색해서 알아보세요 (validation)

//  @Transactional
// @noargument 찾아보기 + argu


//에러메세지별표시하기(세션에서찾아보세요!)