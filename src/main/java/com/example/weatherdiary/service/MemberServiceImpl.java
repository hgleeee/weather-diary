package com.example.weatherdiary.service;

import com.example.weatherdiary.dto.LoginIdAndPassword;
import com.example.weatherdiary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Optional<String> findMemberLoginId(LoginIdAndPassword loginIdAndPassword) {
        return memberRepository.findMemberLoginId(loginIdAndPassword);
    }

    @Override
    public void signUpMember() {

    }
}
