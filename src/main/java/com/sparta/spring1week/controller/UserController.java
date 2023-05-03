package com.sparta.spring1week.controller;

import com.sparta.spring1week.dto.*;
import com.sparta.spring1week.entity.User;
import com.sparta.spring1week.security.UserDetailsImpl;
import com.sparta.spring1week.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//Class이름은 맨앞이 대문자
public class UserController {

    private final UserService userService;

    @PostMapping("/api/auth/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {

      return userService.signup(signupRequestDto);
    }

    @PostMapping("/api/auth/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        return userService.login(loginRequestDto, response);
    }

    @DeleteMapping("/api/auth/delete")
    public LoginResponseDto deleteuser(@RequestBody LoginRequestDto loginRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return userService.deleteuser(loginRequestDto, userDetails.getUser());
    }

}
