package com.sparta.crudbasedjwt.repository;

import com.sparta.crudbasedjwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); //user entity 와 연결 !

}


//Optional<User> findByIdAndUsername(Long id, String username);
