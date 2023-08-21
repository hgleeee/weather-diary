package com.example.weatherdiary.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberSignUpParam {

    @NotBlank(message = "아이디를 입력해주세요.")
    private final String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;
}
