package com.sparta.spring1week.controller;

import com.sparta.spring1week.dto.*;
import com.sparta.spring1week.entity.User;
import com.sparta.spring1week.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//Class이름은 맨앞이 대문자
public class UserController {

    private final UserService userService;

    @PostMapping("/api/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {

      return userService.signup(signupRequestDto);
    }

    @PostMapping("/api/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        return userService.login(loginRequestDto, response);
    }

}
