package com.sparta.hanghaeboard.repository;

import com.sparta.hanghaeboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> { //user entity 와 연결
    Optional<User> findByUsername(String username);
    Optional<User> findByIdAndUsername(Long id, String username);

}