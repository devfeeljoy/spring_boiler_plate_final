package com.sparta.spring1week.repository;

import com.sparta.spring1week.entity.Blog;
import com.sparta.spring1week.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);

}
