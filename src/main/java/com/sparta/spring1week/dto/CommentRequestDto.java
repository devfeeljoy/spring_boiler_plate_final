package com.sparta.spring1week.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private long postId;
    private String contents;
}
