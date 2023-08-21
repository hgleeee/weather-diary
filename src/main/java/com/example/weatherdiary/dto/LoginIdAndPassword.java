package com.example.weatherdiary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginIdAndPassword {

    @NotBlank(message = "아이디를 입력해주세요")
    private final String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private final String password;
}
