package com.sparta.spring1week.controller;

import com.sparta.spring1week.dto.BlogDeleteDto;
import com.sparta.spring1week.dto.BlogRequestDto;
import com.sparta.spring1week.dto.BlogResponseDto;
import com.sparta.spring1week.entity.User;
import com.sparta.spring1week.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/create")
    public BlogResponseDto createList(@RequestBody BlogRequestDto requestDto, HttpServletRequest request){

        return blogService.createList(requestDto, request);
    }

    //조회부분은 수정할 필요가 없음
    @GetMapping("/list")
    public List<BlogResponseDto> getlist(){
        return blogService.getlist();
    }

    @GetMapping("/{id}")
    public BlogResponseDto getidList(@PathVariable Long id) {
        return (BlogResponseDto) blogService.getidlist(id);
    }

    @PutMapping("/update/{id}")
    public BlogResponseDto updateCourse(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.updateBlog(id, requestDto, request);
    }

    @DeleteMapping("/delete/{id}")
    public BlogDeleteDto deleteblog(@PathVariable Long id, HttpServletRequest request){
        return blogService.deleteBlog(id, request);
    }


}
