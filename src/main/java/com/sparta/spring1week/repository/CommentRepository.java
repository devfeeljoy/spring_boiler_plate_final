package com.sparta.spring1week.repository;

import com.sparta.spring1week.entity.Blog;
import com.sparta.spring1week.entity.Comment;
import com.sparta.spring1week.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogOrderByModifiedAtDesc(Blog blog);
}
