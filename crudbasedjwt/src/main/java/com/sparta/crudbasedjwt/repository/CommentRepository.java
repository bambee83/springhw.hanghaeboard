package com.sparta.crudbasedjwt.repository;

import com.sparta.crudbasedjwt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    Optional<Comment> findByIdAndUserId( Long id, Long commentId);

}
