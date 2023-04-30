package com.sparta.spring1week.service;

import com.sparta.spring1week.dto.LoginRequestDto;
import com.sparta.spring1week.dto.LoginResponseDto;
import com.sparta.spring1week.dto.SignupRequestDto;
import com.sparta.spring1week.dto.SignupResponseDto;
import com.sparta.spring1week.entity.User;
import com.sparta.spring1week.entity.UserRoleEnum;
import com.sparta.spring1week.exception.BusinessException;
import com.sparta.spring1week.exception.ErrorCode;
import com.sparta.spring1week.jwt.JwtUtil;
import com.sparta.spring1week.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        //Optional 공부가 필요
        if (found.isPresent()) {

            throw new BusinessException(ErrorCode.USER_DUF_ERROR);
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new BusinessException(ErrorCode.ADMIN_ERROR);
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
                () -> new BusinessException(ErrorCode.USER_ERROR)
        );
        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return new LoginResponseDto("로그인에 성공하셨습니다.", 200);

    }
}
