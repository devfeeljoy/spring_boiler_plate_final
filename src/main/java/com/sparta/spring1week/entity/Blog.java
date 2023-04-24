package com.sparta.spring1week.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.spring1week.dto.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String username;
    private String contents;

    //하나의 유저는 여러 게시글을 가질수있다
    @ManyToOne //바로가져온다 fetchtype eager
    @JoinColumn(name="userId", nullable = false)
    private User user; // DB에서 오브젝트 저장할수 없다. FK를 사용

    //하나의 게시글은 여러 댓글으 가질수 있다
    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE)//mappedBy가 적히면 연관관계의 주인이아니다(fk가 아님) DB에 컬럼을 만들지 않는다. 부모가 삭제시 자식도 삭제cascade
    //select시 join해서 정보만 가져온다.
    private List<Comment> comment;




    public Blog(BlogRequestDto requestDto, String username, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        //username을 받아오기위해 추가
        this.username = username;
        this.user = user;

    }

    public void addcomment(List<Comment> comment){
        this.comment = comment;
    }


    public void update(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();

    }
}
