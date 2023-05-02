package com.sparta.spring1week.repository;

import com.sparta.spring1week.entity.Blog;
import com.sparta.spring1week.entity.LikeBlog;
import com.sparta.spring1week.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeBlogRepository extends JpaRepository<LikeBlog, Long> {
    boolean existsByBlogAndUser(Blog blog, User user);

    void deleteByBlogAndUser(Blog blog, User user);

    List<LikeBlog> findAllByBlog(Blog blog);
}