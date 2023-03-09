package com.sparta.crudbasedjwt.repository;


import com.sparta.crudbasedjwt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//jpa repo 의 상속을 받기 때문에 @repository 안 붙여준다고 이해하면 되나요 ?
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();
    Optional<Post> findByIdAndUsername(Long id, String username);
}
