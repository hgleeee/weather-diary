package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
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
        Assertions.assertEquals(2, memberRepository.count());
        findById_validation(savedMember1, findMember1);
        findById_validation(savedMember2, findMember2);
    }

    private void findById_validation(Member savedMember, Member findMember) {
        Assertions.assertEquals(savedMember.getName(), findMember.getName());
        Assertions.assertEquals(savedMember.getEmail(), findMember.getEmail());
        Assertions.assertEquals(savedMember.getLoginId(), findMember.getLoginId());
        Assertions.assertEquals(savedMember.getPassword(), findMember.getPassword());
    }

    @Test
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
}