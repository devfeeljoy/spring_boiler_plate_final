package com.sparta.spring1week.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignupRequestDto {
    @NotNull(message = "id는 필수 값입니다.")
    //패턴의 정규식표현에 +를 붙여줘야한다.. 이것땜에 헤멤
    @Pattern(regexp = "[a-z0-9]+")
    @Size(min = 4, max = 10)
    private String username;

    @NotNull(message = "pw는 필수 값입니다.")
    @Pattern(regexp = "[a-zA-Z0-9@#$%^&+=!]+")
    @Size(min = 8, max = 15)
    private String password;

    private boolean admin = false;
    private String adminToken = "";



}

