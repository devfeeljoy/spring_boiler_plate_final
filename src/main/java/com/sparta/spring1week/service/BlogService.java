package com.sparta.spring1week.service;

import com.sparta.spring1week.dto.ResponseCodeDto;
import com.sparta.spring1week.dto.BlogRequestDto;
import com.sparta.spring1week.dto.BlogResponseDto;
import com.sparta.spring1week.entity.Blog;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    //BlogRensponseDto를 사용하여 password빼고 추출
    @Transactional
    public BlogResponseDto createList(BlogRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            } else{
                throw new BusinessExceptionHandler(ErrorCode.TOKEN_ERROR);
            }


        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new BusinessExceptionHandler(ErrorCode.USER_ERROR)
        );

        // username 받아온 값을 추가, user값 추가(user의 주소?)
        Blog blog =  blogRepository.saveAndFlush(new Blog(requestDto, user.getUsername(), user));


        return new BlogResponseDto(blog);
        }else{
            throw new BusinessExceptionHandler(ErrorCode.TOKEN_NULL_ERROR);
        }

}


    public List<BlogResponseDto> getlist(){
        List<Blog> bloglist = blogRepository.findAllByOrderByModifiedAtDesc();
        //List<Comment> commnet = commentRepository.findAll();
        List<BlogResponseDto> blogResponseDto = new ArrayList<>();
        //내림차순된 정보를 가지고와서 responsedto에 넣어줌
        for (Blog blog : bloglist){
            BlogResponseDto a = new BlogResponseDto(blog);
            blogResponseDto.add(a);
        }
        return blogResponseDto;
    }



    public BlogResponseDto getidlist(Long id) {
        Blog blog = checkblog(id);

        return new BlogResponseDto(blog);
    }




    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, HttpServletRequest request) {
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


            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            Blog blog = checkblog(id);
            if(user.getUsername().equals(blog.getUsername())) {
                blog.update(requestDto);
                return new BlogResponseDto(blog);
            } else if(userRoleEnum == UserRoleEnum.ADMIN){
                blog.update(requestDto);
                return new BlogResponseDto(blog);
            }
            else{
                throw new BusinessExceptionHandler(ErrorCode.MODIFY_ERROR);
            }

        }else {
            throw new BusinessExceptionHandler(ErrorCode.TOKEN_NULL_ERROR);
        }
    }

    private Blog checkblog(Long id) {
        return blogRepository.findById(id).orElseThrow(
                () -> new BusinessExceptionHandler(ErrorCode.BLOG_ERROR)
        );
    }


    @Transactional
    public ResponseCodeDto deleteBlog(Long id, HttpServletRequest request) {
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
            Blog blog = checkblog(id);
            UserRoleEnum userRoleEnum = user.getRole();
            if(user.getUsername().equals(blog.getUsername())) {
                blogRepository.deleteById(id);
                return new ResponseCodeDto("게시글 삭제 성공.", 200);
            }
            else if(userRoleEnum == UserRoleEnum.ADMIN){
                blogRepository.deleteById(id);
                return new ResponseCodeDto("관리자가 게시글 삭제 성공.", 200);
            }
            else{
                throw new BusinessExceptionHandler(ErrorCode.MODIFY_ERROR);
            }


        }else{
            throw new BusinessExceptionHandler(ErrorCode.TOKEN_NULL_ERROR);
        }

    }

}
