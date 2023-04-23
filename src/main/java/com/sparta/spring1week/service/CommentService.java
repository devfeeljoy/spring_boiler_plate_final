package com.sparta.spring1week.service;

import com.sparta.spring1week.dto.ResponseCodeDto;
import com.sparta.spring1week.dto.CommentRequestDto;
import com.sparta.spring1week.dto.CommentResponseDto;
import com.sparta.spring1week.entity.Blog;
import com.sparta.spring1week.entity.Comment;
import com.sparta.spring1week.entity.User;
import com.sparta.spring1week.entity.UserRoleEnum;
import com.sparta.spring1week.exception.BusinessExceptionHandler;
import com.sparta.spring1week.exception.ErrorCode;
import com.sparta.spring1week.jwt.JwtUtil;
import com.sparta.spring1week.repository.BlogRepository;
import com.sparta.spring1week.repository.CommentRepository;
import com.sparta.spring1week.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    private final JwtUtil jwtUtil;



    public CommentResponseDto createcomment(CommentRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new BusinessExceptionHandler(ErrorCode.TOKEN_ERROR);
            }


            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(

                    () -> new BusinessExceptionHandler(ErrorCode.USER_ERROR)
            );
            Blog blog = blogRepository.findById(requestDto.getPostId()).orElseThrow(
                    () -> new BusinessExceptionHandler(ErrorCode.BLOG_ERROR)
            );

            Comment comment =  commentRepository.saveAndFlush(new Comment(requestDto, user.getUsername(),blog ,user));
            List<Comment> commnetadd = commentRepository.findByBlogOrderByModifiedAtDesc(blog);
            blog.addcomment(commnetadd);
            return new CommentResponseDto(comment);


        }
        else{
            throw new BusinessExceptionHandler(ErrorCode.TOKEN_NULL_ERROR);
        }

    }
    @Transactional
    public CommentResponseDto updatecommnet(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new BusinessExceptionHandler(ErrorCode.TOKEN_ERROR);
            }


            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(

                    () -> new BusinessExceptionHandler(ErrorCode.USER_ERROR)
            );
            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new BusinessExceptionHandler(ErrorCode.COMMENT_ERROR)
            );

            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            if(user.getUsername().equals(comment.getUsername())) {
                comment.update(requestDto);
                return new CommentResponseDto(comment);
            } else if(userRoleEnum == UserRoleEnum.ADMIN){
                comment.update(requestDto);
                return new CommentResponseDto(comment);
            }
            else{
                throw new BusinessExceptionHandler(ErrorCode.MODIFY_ERROR);
            }
        }else {
            throw new BusinessExceptionHandler(ErrorCode.TOKEN_NULL_ERROR);
        }


    }
    @Transactional
    public ResponseCodeDto deletecomment(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new BusinessExceptionHandler(ErrorCode.TOKEN_ERROR);
            }


            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(

                    () -> new BusinessExceptionHandler(ErrorCode.USER_ERROR)
            );
            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new BusinessExceptionHandler(ErrorCode.COMMENT_ERROR)
            );

            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            if(user.getUsername().equals(comment.getUsername())) {
                commentRepository.deleteById(id);
                return new ResponseCodeDto("사용자가 댓글 삭제 성공.", 200);
            } else if(userRoleEnum == UserRoleEnum.ADMIN){
                commentRepository.deleteById(id);
                return new ResponseCodeDto("관리자가 게시글 삭제 성공.", 200);
            }
            else{
                throw new BusinessExceptionHandler(ErrorCode.MODIFY_ERROR);
            }
        }else {
            throw new BusinessExceptionHandler(ErrorCode.TOKEN_NULL_ERROR);
        }
    }
}
