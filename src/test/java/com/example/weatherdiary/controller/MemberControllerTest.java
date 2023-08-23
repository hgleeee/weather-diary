package com.example.weatherdiary.controller;

import com.example.weatherdiary.dto.MemberSignUpParam;
import com.example.weatherdiary.exception.NotUniqueLoginIdException;
import com.example.weatherdiary.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    void signUpMemberWithSuccess() throws Exception {
        // given
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("loginId", "test");
        paramMap.add("password", "test");
        paramMap.add("name", "lee");
        paramMap.add("email", "test@test.com");

        doNothing().when(memberService).signUpMember(any(MemberSignUpParam.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(paramMap));

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 아이디 입력")
    void signUpMemberWithFailure() throws Exception {
        // given
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("loginId", "test");
        paramMap.add("password", "test");
        paramMap.add("name", "lee");
        paramMap.add("email", "test@test.com");

        doThrow(NotUniqueLoginIdException.class).when(memberService).signUpMember(any(MemberSignUpParam.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(paramMap));

        // then
        resultActions.andExpect(status().isConflict());
    }

    @Test
    @DisplayName("중복 아이디 체크 - 중복 x")
    void checkLoginIdDuplicatedWithSuccess() throws Exception {
        doNothing().when(memberService).checkLoginIdDuplicated("test");

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/member/test/exist"));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("중복 아이디 체크 - 중복 o")
    void checkLoginIdDuplicatedWithFailure() throws Exception {
        doThrow(NotUniqueLoginIdException.class).when(memberService).checkLoginIdDuplicated("test");

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/member/test/exist"));

        // then
        resultActions.andExpect(status().isConflict());
    }
}