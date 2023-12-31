package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.LoginIdAndPassword;
import com.example.weatherdiary.exception.InvalidValueException;
import com.example.weatherdiary.exception.NotUniqueLoginIdException;
import com.example.weatherdiary.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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

    @DisplayName("로그인 시 아이디 비밀번호 일치")
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

    @DisplayName("로그인 시 아이디 비밀번호 불일치")
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

    @Test
    @DisplayName("중복 아이디 검사")
    void checkLoginIdDuplicated() {
        // given
        when(memberRepository.findByLoginId(member.getLoginId())).thenReturn(Optional.of(member));

        // when

        // then
        assertThrows(NotUniqueLoginIdException.class, () -> memberService.checkLoginIdDuplicated(member.getLoginId()),
                String.format("%s는 중복된 아이디입니다.", member.getLoginId()));
    }

    @Test
    @DisplayName("회원 탈퇴 성공")
    void deleteMemberWithSuccess() {
        assertDoesNotThrow(() -> {
            memberService.deleteMember(member, "test");
        });
    }

    @Test
    @DisplayName("회원 탈퇴 실패")
    void deleteMemberWithFailure() {
        assertThrows(InvalidValueException.class, () -> {
            memberService.deleteMember(member, "test1");
        }, "비밀번호가 일치하지 않습니다.");
    }

}