package com.sparta.spring1week.entity;

import com.sparta.spring1week.dto.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private long password;
    private String username;
    private String contents;


    public Blog(BlogRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        //username을 받아오기위해 추가
        this.username = username;
    }

    public void update(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();

    }
}
