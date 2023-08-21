package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.LoginIdAndPassword;
import com.example.weatherdiary.dto.MemberSignUpParam;
import com.example.weatherdiary.exception.NotUniqueLoginIdException;
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
    public void signUpMember(MemberSignUpParam memberSignUpParam) {
        memberRepository.save(Member.builder()
                .loginId(memberSignUpParam.getLoginId())
                .password(memberSignUpParam.getPassword())
                .name(memberSignUpParam.getName())
                .email(memberSignUpParam.getEmail())
                .build());
    }

    @Override
    public void checkLoginIdDuplicated(String loginId) throws NotUniqueLoginIdException {
        if (!memberRepository.findByLoginId(loginId).isEmpty()) {
            throw new NotUniqueLoginIdException(String.format("%s는 중복된 아이디입니다.", loginId));
        }
    }
}
