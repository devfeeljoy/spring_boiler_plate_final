package com.sparta.spring1week.controller;

import com.sparta.spring1week.dto.*;
import com.sparta.spring1week.security.UserDetailsImpl;
import com.sparta.spring1week.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public CommentResponseDto createcomment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.createcomment(requestDto,  userDetails.getUser());
    }

    @PutMapping("/api/comment/{id}")
    public CommentResponseDto updateCourse(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updatecommnet(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseCodeDto deleteblog(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deletecomment(id, userDetails.getUser());
    }

    @PostMapping("/api/comment/like/{id}")
    public CommentResponseDto createcomment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.likecomment(id,  userDetails.getUser());
    }
}
