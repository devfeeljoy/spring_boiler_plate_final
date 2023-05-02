package com.sparta.spring1week.repository;

import com.sparta.spring1week.entity.Comment;
import com.sparta.spring1week.entity.LikeComment;
import com.sparta.spring1week.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    boolean existsByCommentAndUser(Comment comment, User user);

    void deleteByCommentAndUser(Comment comment, User user);

    List<LikeComment> findAllByComment(Comment comment);
}