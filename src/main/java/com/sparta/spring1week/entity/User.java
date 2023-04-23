package com.sparta.spring1week.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name ="users") //강의에서도 했지만 table명 user로하면 오류가난다.(기본값 사용)
@Entity // User 클래스 MYsql에 생성
public class User {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    //@OneToMany
    //private List<Comment> comments = new ArrayList<>();

    public User(String username, String password, UserRoleEnum role ) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}