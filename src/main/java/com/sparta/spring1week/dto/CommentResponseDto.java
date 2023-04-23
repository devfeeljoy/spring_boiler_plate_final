package com.sparta.spring1week.dto;

import com.sparta.spring1week.entity.Blog;
import com.sparta.spring1week.entity.Comment;
import com.sparta.spring1week.entity.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto{
    private Long id;
    private String username;
    private String contents;
    private LocalDateTime CreatedAt;
    private LocalDateTime ModifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.CreatedAt = comment.getCreatedAt();
        this.ModifiedAt = comment.getModifiedAt();
        this.username = comment.getUsername();
    }
}
