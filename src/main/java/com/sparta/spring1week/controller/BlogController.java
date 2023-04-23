package com.sparta.spring1week.controller;

import com.sparta.spring1week.dto.ResponseCodeDto;
import com.sparta.spring1week.dto.BlogRequestDto;
import com.sparta.spring1week.dto.BlogResponseDto;
import com.sparta.spring1week.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/api/post")
    public BlogResponseDto createList(@RequestBody BlogRequestDto requestDto, HttpServletRequest request){

        return blogService.createList(requestDto, request);
    }

    //조회부분은 수정할 필요가 없음
    @GetMapping("/api/posts")
    public List<BlogResponseDto> getlist(){
        return blogService.getlist();
    }

    @GetMapping("/api/post/{id}")
    public BlogResponseDto getidList(@PathVariable Long id) {
        return (BlogResponseDto) blogService.getidlist(id);
    }

    @PutMapping("/api/post/{id}")
    public BlogResponseDto updateCourse(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.updateBlog(id, requestDto, request);
    }

    @DeleteMapping("/api/post/{id}")
    public ResponseCodeDto deleteblog(@PathVariable Long id, HttpServletRequest request){
        return blogService.deleteBlog(id, request);
    }




}
