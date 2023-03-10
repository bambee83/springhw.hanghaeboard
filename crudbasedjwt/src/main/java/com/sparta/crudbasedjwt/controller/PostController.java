package com.sparta.crudbasedjwt.controller;

import com.sparta.crudbasedjwt.dto.PostListResponseDto;
import com.sparta.crudbasedjwt.dto.PostRequestDto;
import com.sparta.crudbasedjwt.dto.PostResponseDto;
import com.sparta.crudbasedjwt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController @RequiredArgsConstructor //이거없으면 의존성 주입 안됨
@RequestMapping("/post")
public class PostController {
    private final PostService postService; //Model 계층의 service 를 이용하기 위한 선언(의존성주입? 상속?)
    //게시글 작성
    @PostMapping("/create")  //PostRequestDto 는 Post 의 내용을 값으로 가지고 있다
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.createPost(postRequestDto, request);} //계층이 다르기 때문에 어떤 계층에도 속하지 않은 requestDto 를 이용한다
    //전체 게시글 목록 조회 > 데이터를 받아올 게 없음 (get 방식)
    @GetMapping("/list")
    public List<PostListResponseDto> getPostList() {return postService.getPostList();}
    //선택 게시글 조회 (게시글의 id를 특정하여 받아온다)
    @GetMapping("/list/{id}")
    public PostListResponseDto getPost(@PathVariable Long id) {return postService.getPost(id);}
    //선택 게시글 수정 > id와 수정될 정보를 받아온다
    @PutMapping("/update/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);}
    //선택 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePost(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok().body(postService.deletePost(id, request));}
} // 반환타입정리 필요 ! /삭제시 dto 는 안받아와도 될듯
