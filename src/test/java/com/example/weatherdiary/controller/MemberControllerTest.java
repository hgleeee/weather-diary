package com.example.weatherdiary.controller;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.MemberSignUpParam;
import com.example.weatherdiary.exception.InvalidValueException;
import com.example.weatherdiary.exception.NotUniqueLoginIdException;
import com.example.weatherdiary.repository.MemberRepository;
import com.example.weatherdiary.resolver.CurrentMemberArgumentResolver;
import com.example.weatherdiary.service.MemberService;
import jdk.jfr.ContentType;
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

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    CurrentMemberArgumentResolver currentMemberArgumentResolver;

    @MockBean
    LoginController loginController;

    @Test
    @DisplayName("회원가입 성공")
    void signUpMemberWithSuccess() throws Exception {
        // given
        String requestJson = "{\"loginId\":\"test\", \"password\":\"test\", \"name\":\"lee\", \"email\":\"test@test.com\"}";
        doNothing().when(memberService).signUpMember(any(MemberSignUpParam.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 아이디 입력")
    void signUpMemberWithFailure() throws Exception {
        // given
        String requestJson = "{\"loginId\":\"test\", \"password\":\"test\", \"name\":\"lee\", \"email\":\"test@test.com\"}";
        doThrow(NotUniqueLoginIdException.class).when(memberService).signUpMember(any(MemberSignUpParam.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));

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

    @Test
    @DisplayName("회원 탈퇴 성공")
    void deleteMemberWithSuccess() throws Exception {

        // given
        doNothing().when(memberService).deleteMember(any(Member.class), eq("test"));

        // when
        // then
        mockMvc.perform(
                delete("/member/myAccount")
                        .param("password", "test"))
                .andExpect(status().isOk());
        verify(memberService).deleteMember(any(Member.class), eq("test"));
        verify(loginController).logout();
    }

    @Test
    @DisplayName("회원 탈퇴 실패 - 비밀번호 틀림")
    void deleteMemberWithFailure() throws Exception {
        // given
        doThrow(InvalidValueException.class).when(memberService).deleteMember(any(Member.class), anyString());

        // when
        // then
        mockMvc.perform(
                        delete("/member/myAccount")
                                .param("password", "wrong"))
                .andExpect(status().isUnauthorized());
    }
}