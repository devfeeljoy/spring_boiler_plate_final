package com.sparta.spring1week.controller;

import com.sparta.spring1week.dto.BlogRequestDto;
import com.sparta.spring1week.dto.BlogResponseDto;
import com.sparta.spring1week.dto.CommentRequestDto;
import com.sparta.spring1week.dto.CommentResponseDto;
import com.sparta.spring1week.service.BlogService;
import com.sparta.spring1week.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public CommentResponseDto createList(@RequestBody CommentRequestDto requestDto, HttpServletRequest request){

        return null;
    }
}
