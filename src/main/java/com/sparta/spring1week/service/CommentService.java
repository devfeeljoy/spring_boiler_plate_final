package com.sparta.spring1week.service;

import com.sparta.spring1week.dto.ResponseCodeDto;
import com.sparta.spring1week.dto.CommentRequestDto;
import com.sparta.spring1week.dto.CommentResponseDto;
import com.sparta.spring1week.entity.*;
import com.sparta.spring1week.exception.BusinessException;
import com.sparta.spring1week.exception.ErrorCode;
import com.sparta.spring1week.repository.BlogRepository;
import com.sparta.spring1week.repository.CommentRepository;
import com.sparta.spring1week.repository.LikeCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final LikeCommentRepository likeCommentRepository;


    public CommentResponseDto createcomment(CommentRequestDto requestDto, User user) {

        Blog blog = blogRepository.findById(requestDto.getPostId()).orElseThrow(
                () -> new BusinessException(ErrorCode.BLOG_ERROR)
        );

        Comment comment = commentRepository.saveAndFlush(new Comment(requestDto, blog, user));
        List<Comment> commnetadd = commentRepository.findByBlogOrderByModifiedAtDesc(blog);
        blog.addcomment(commnetadd);
        return new CommentResponseDto(comment);

    }


    @Transactional
    public CommentResponseDto updatecommnet(Long id, CommentRequestDto requestDto, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_ERROR)
        );

        UserRoleEnum userRoleEnum = user.getRole();

        if (user.getUsername().equals(comment.getUsername())) {
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else if (userRoleEnum == UserRoleEnum.ADMIN) {
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new BusinessException(ErrorCode.MODIFY_ERROR);
        }

    }

    @Transactional
    public ResponseCodeDto deletecomment(Long id, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_ERROR)
        );

        UserRoleEnum userRoleEnum = user.getRole();

        if (user.getUsername().equals(comment.getUsername())) {
            commentRepository.deleteById(id);
            return new ResponseCodeDto("사용자가 댓글 삭제 성공.", 200);
        } else if (userRoleEnum == UserRoleEnum.ADMIN) {
            commentRepository.deleteById(id);
            return new ResponseCodeDto("관리자가 게시글 삭제 성공.", 200);
        } else {
            throw new BusinessException(ErrorCode.MODIFY_ERROR);
        }

    }

    public CommentResponseDto likecomment(Long id, User user) {
        // username 받아온 값을 추가, user값 추가(user의 주소?)
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_ERROR)
        );

        boolean exists = likeCommentRepository.existsByCommentAndUser(comment, user);
        if (exists){
            likeCommentRepository.deleteByCommentAndUser(comment, user);
        }else{
            LikeComment likecomment = new LikeComment();
            likecomment.setBlog(comment);
            likecomment.setUser(user);
            likeCommentRepository.save(likecomment);
        }
        List<LikeComment> count =  likeCommentRepository.findAllByComment(comment);
        comment.count(count.size());

        return new CommentResponseDto(comment);
    }
}
