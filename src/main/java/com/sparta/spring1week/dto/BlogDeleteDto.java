package com.sparta.spring1week.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlogDeleteDto {
    private String msg;
    private int statusCod;

    public BlogDeleteDto(String msg, int statusCod) {
        this.msg = msg;
        this.statusCod = statusCod;
    }
}
