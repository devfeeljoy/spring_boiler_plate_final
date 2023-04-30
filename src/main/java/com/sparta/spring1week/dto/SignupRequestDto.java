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
    //+ 혹은 $표시를 통해 정규식이 끝낫다는것을 알려줌.
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

