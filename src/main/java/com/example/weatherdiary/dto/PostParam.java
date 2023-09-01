package com.example.weatherdiary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostParam {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min=8, message = "제목은 최소 8자 이상이어야 합니다.")
    private String title;

    @NotBlank(message = "본문을 입력해주세요.")
    private String content;
}
