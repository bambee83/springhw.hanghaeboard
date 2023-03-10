package com.sparta.crudbasedjwt.repository;


import com.sparta.crudbasedjwt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//jpa repo 의 상속을 받기 때문에 @repository 안 붙여준다고 이해하면 되나요 ?
public interface PostRepository extends JpaRepository<Post, Long> {
//    Optional<Post> findByIdAndUserId(Long id, Long userId);

    List<Post> findAllByOrderByCreatedAtDesc();
    Optional<Post> findByIdAndUsername(Long id, String username);



    //@Query (업데이트를 하는 엄청난 업데이트 코드가 있습니다  쿼리문을 비용을 줄여준다 !! )
    // void ~~
    // 이그지스트문 빠르긴함 유저만 가져오기 보통 옵셔널 꼴찌
}
