package com.example.weatherdiary.controller;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.LoginIdAndPassword;
import com.example.weatherdiary.repository.MemberRepository;
import com.example.weatherdiary.resolver.CurrentMemberArgumentResolver;
import com.example.weatherdiary.service.MemberService;
import com.example.weatherdiary.utils.SessionConst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @MockBean
    MemberRepository memberRepository;

    @Mock
    MockHttpSession mockHttpSession;

    Member member;

    @BeforeEach
    void beforeEach() {
        member = Member.builder()
                .loginId("test")
                .password("test")
                .name("lee")
                .email("test@test.com")
                .build();

        mockHttpSession = new MockHttpSession();
    }

    @Test
    @DisplayName("로그인 성공")
    void loginWithSuccess() throws Exception {

        // given
        String requestJson = "{\"loginId\":\"test\", \"password\":\"test\"}";
        when(memberService.findMemberLoginId(any(LoginIdAndPassword.class))).thenReturn(Optional.of(member.getLoginId()));

        // when
        // then
        mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());

        verify(memberService).findMemberLoginId(any(LoginIdAndPassword.class));
    }

    @Test
    @DisplayName("로그인 시 아이디/비밀번호가 잘못되어 로그인 실패")
    void loginInvalidWithFailure() throws Exception {

        // given
        String requestJson = "{\"loginId\":\"wrong\", \"password\":\"wrong\"}";
        when(memberService.findMemberLoginId(any(LoginIdAndPassword.class))).thenReturn(Optional.empty());

        // when
        // then
        mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(memberService).findMemberLoginId(any(LoginIdAndPassword.class));
    }

    @Test
    @DisplayName("이미 로그인되어있는 상태에서 로그인 실패")
    void loginAlreadyWithFailure() throws Exception {

        // given
        mockHttpSession.setAttribute(SessionConst.LOGIN_ID, member.getLoginId());
        when(memberService.findMemberLoginId(any(LoginIdAndPassword.class))).thenReturn(Optional.of(member.getLoginId()));
        String requestJson = "{\"loginId\":\"test\", \"password\":\"test\"}";
        // when
        // then
        mockMvc.perform(
                        post("/login").session(mockHttpSession)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logout() throws Exception {

        // given
        mockHttpSession.setAttribute(SessionConst.LOGIN_ID, member.getLoginId());

        // when
        mockMvc.perform(
                post("/logout").session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertTrue(mockHttpSession.isInvalid());
    }
}