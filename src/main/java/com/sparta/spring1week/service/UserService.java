package com.sparta.spring1week.service;

import com.sparta.spring1week.dto.LoginRequestDto;
import com.sparta.spring1week.dto.LoginResponseDto;
import com.sparta.spring1week.dto.SignupRequestDto;
import com.sparta.spring1week.dto.SignupResponseDto;
import com.sparta.spring1week.entity.User;
import com.sparta.spring1week.entity.UserRoleEnum;
import com.sparta.spring1week.jwt.JwtUtil;
import com.sparta.spring1week.repository.UserRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        //Optional 공부가 필요
        if (found.isPresent()) {

            return new SignupResponseDto("이미 존재한는 아이디입니다.", 400);
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);

        return new SignupResponseDto("회원가입에 성공하셨습니다" , 200);
    }


    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return new LoginResponseDto("로그인에 성공하셨습니다.", 200);

    }
}
