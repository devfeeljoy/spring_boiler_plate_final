package com.sparta.spring1week.controller;

import com.sparta.spring1week.dto.*;
import com.sparta.spring1week.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public CommentResponseDto createcomment(@RequestBody CommentRequestDto requestDto, HttpServletRequest request){

        return commentService.createcomment(requestDto, request);
    }

    @PutMapping("/api/comment/{id}")
    public CommentResponseDto updateCourse(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.updatecommnet(id, requestDto, request);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseCodeDto deleteblog(@PathVariable Long id, HttpServletRequest request){
        return commentService.deletecomment(id, request);
    }
}
