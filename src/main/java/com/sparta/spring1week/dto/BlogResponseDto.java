package com.sparta.spring1week.dto;

import com.sparta.spring1week.entity.Blog;
import com.sparta.spring1week.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String title;
    private String username;
    private String contents;
    private LocalDateTime CreatedAt;
    private LocalDateTime ModifiedAt;
    private List<Comment> comment;
    private int likes;

    public BlogResponseDto(Blog blog) {
        this.CreatedAt = blog.getCreatedAt();
        this.ModifiedAt = blog.getModifiedAt();
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.username = blog.getUsername();
        this.contents = blog.getContents();
        this.comment = blog.getComment();
        this.likes = blog.getLikes();

    }

    //public static class
}
