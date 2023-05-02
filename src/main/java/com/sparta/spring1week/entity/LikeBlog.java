package com.sparta.spring1week.entity;

import jakarta.persistence.*;

@Entity
public class LikeBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
