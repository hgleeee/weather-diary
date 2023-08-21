package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.LoginIdAndPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("JpaRepository -> save()")
    void save() {
        // given
        Member member = Member.builder()
                .loginId("test")
                .password("test")
                .name("lee")
                .build();
        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertEquals("test", savedMember.getLoginId());
        assertEquals("test", savedMember.getPassword());
        assertEquals("lee", savedMember.getName());
    }

    @Test
    @DisplayName("JpaRepository -> findById()")
    void findById() {
        // given
        Member savedMember1 = memberRepository.save(Member.builder()
                .loginId("test1")
                .password("test1")
                .name("lee")
                .email("test1@test.com")
                .build());
        Member savedMember2 = memberRepository.save(Member.builder()
                .loginId("test2")
                .password("test2")
                .name("kim")
                .email("test2@test.com")
                .build());
        // when
        Member findMember1 = memberRepository.findById(savedMember1.getId()).orElseThrow(() -> new IllegalArgumentException());
        Member findMember2 = memberRepository.findById(savedMember2.getId()).orElseThrow(() -> new IllegalArgumentException());

        // then
        assertEquals(2, memberRepository.count());
        findById_validation(savedMember1, findMember1);
        findById_validation(savedMember2, findMember2);
    }

    private void findById_validation(Member savedMember, Member findMember) {
        assertEquals(savedMember.getName(), findMember.getName());
        assertEquals(savedMember.getEmail(), findMember.getEmail());
        assertEquals(savedMember.getLoginId(), findMember.getLoginId());
        assertEquals(savedMember.getPassword(), findMember.getPassword());
    }

    @Test
    @DisplayName("JpaRepository -> delete()")
    void delete() {
        // given
        Member savedMember = memberRepository.save(Member.builder()
                .loginId("test")
                .password("test")
                .name("lee")
                .email("test@test.com")
                .build());

        // when
        memberRepository.delete(memberRepository.findById(savedMember.getId()).orElseThrow(() -> new RuntimeException()));

        // then
        assertEquals(Optional.empty(), memberRepository.findById(savedMember.getId()));
    }

    @Test
    @DisplayName("Custom -> 로그인 성공")
    void loginWithSuccess() {
        // given
        Member savedMember = memberRepository.save(Member.builder()
                .loginId("test")
                .password("test")
                .name("lee")
                .email("test@test.com")
                .build());
        LoginIdAndPassword loginIdAndPassword = new LoginIdAndPassword("test", "test");

        // when
        Optional<String> optionalLoginId = memberRepository.findMemberLoginId(loginIdAndPassword);

        // then
        assertEquals(Optional.of("test"), optionalLoginId);
    }

    @Test
    @DisplayName("Custom -> 로그인 실패")
    void loginWithFailure() {
        // given
        Member savedMember = memberRepository.save(Member.builder()
                .loginId("test")
                .password("test")
                .name("lee")
                .email("test@test.com")
                .build());
        LoginIdAndPassword loginIdAndPassword = new LoginIdAndPassword("test", "test2");

        // when
        Optional<String> optionalLoginId = memberRepository.findMemberLoginId(loginIdAndPassword);

        // then
        assertEquals(Optional.empty(), optionalLoginId);
    }

    @Test
    @DisplayName("로그인 아이디로 회원 리스트 가져오기")
    void findByLoginId() {
        // given
        memberRepository.save(Member.builder()
                .loginId("test")
                .password("test")
                .name("lee")
                .email("test@test.com")
                .build());
        memberRepository.save(Member.builder()
                .loginId("test2")
                .password("test2")
                .name("lee")
                .email("test2@test.com")
                .build());
        memberRepository.save(Member.builder()
                .loginId("test2")
                .password("test3")
                .name("lee")
                .email("test3@test.com")
                .build());
        // when
        List<Member> testList = memberRepository.findByLoginId("test");
        List<Member> test2List = memberRepository.findByLoginId("test2");
        List<Member> test3List = memberRepository.findByLoginId("test3");

        // then
        assertEquals(1, testList.size());
        assertEquals(2, test2List.size());
        assertEquals(0, test3List.size());
    }

}