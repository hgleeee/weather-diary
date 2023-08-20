package com.example.weatherdiary.service;

import com.example.weatherdiary.controller.LoginController;
import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.utils.SessionConst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionLoginServiceTest {

    @Mock
    private MockHttpSession mockHttpSession;

    @InjectMocks
    private LoginController sessionLoginService;

    private Member loginMember;

    @BeforeEach
    void beforeEach() {
        loginMember = Member.builder()
                .loginId("test")
                .password("test")
                .name("lee")
                .email("test@test.com")
                .build();
    }

    @Test
    void login() {
        // given
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(mockHttpSession).setAttribute(eq(SessionConst.LOGIN_ID), argumentCaptor.capture());

        // when
        sessionLoginService.login(loginMember.getLoginId());

        // then
        verify(mockHttpSession).setAttribute(SessionConst.LOGIN_ID, loginMember.getLoginId());
        assertEquals(loginMember.getLoginId(), argumentCaptor.getValue());
    }

    @Test
    void logout() {
    }

    @Test
    void getCurrentLoginId() {
        // given
        when(mockHttpSession.getAttribute(SessionConst.LOGIN_ID)).thenReturn(loginMember.getLoginId());

        // when
        mockHttpSession.setAttribute(SessionConst.LOGIN_ID, loginMember.getLoginId());
        String currentLoginId = sessionLoginService.getCurrentLoginId();

        // then
        verify(mockHttpSession).getAttribute(SessionConst.LOGIN_ID);
        assertEquals(loginMember.getLoginId(), currentLoginId);
    }
}