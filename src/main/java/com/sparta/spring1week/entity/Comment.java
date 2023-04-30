package com.sparta.spring1week.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.spring1week.dto.BlogRequestDto;
import com.sparta.spring1week.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"blog", "user"}) //순환참조 해결
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contents;
    private String username;

    //LAZY DB에안들리고
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogId", nullable = false)

    private Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Comment(CommentRequestDto requestDto, Blog blog, User user) {

        this.contents = requestDto.getContents();
        //username을 받아오기위해 추가
        this.username = user.getUsername();
        this.blog = blog;
        this.user = user;

    }

    public void update(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();

    }



}
