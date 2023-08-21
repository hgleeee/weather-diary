package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.LoginIdAndPassword;
import com.example.weatherdiary.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class MemberServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberServiceImpl memberService;

    Member member;

    @BeforeEach
    void beforeEach() {
        member = Member.builder()
                .loginId("test")
                .password("test")
                .name("lee")
                .email("test@test.com")
                .build();
    }

    @Test
    void findMemberLoginIdTestWithSuccess() {

        // given
        LoginIdAndPassword loginIdAndPassword = new LoginIdAndPassword(member.getLoginId(), member.getPassword());
        when(memberRepository.findMemberLoginId(loginIdAndPassword)).thenReturn(Optional.of(member.getLoginId()));

        // when
        Optional<String> optionalLoginId = memberService.findMemberLoginId(loginIdAndPassword);

        // then
        assertEquals(member.getLoginId(), optionalLoginId.orElseThrow(() -> new RuntimeException()));
    }

    @Test
    void findMemberLoginIdTestWithFailure() {

        // given
        LoginIdAndPassword loginIdAndPassword = new LoginIdAndPassword("test2", "test2");
        when(memberRepository.findMemberLoginId(loginIdAndPassword)).thenReturn(Optional.empty());

        // when
        Optional<String> optionalLoginId = memberService.findMemberLoginId(loginIdAndPassword);

        // then
        assertEquals(Optional.empty(), optionalLoginId);
    }

    @Test
    void signUpMember() {
    }
}