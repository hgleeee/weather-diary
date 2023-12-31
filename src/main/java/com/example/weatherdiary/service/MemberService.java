package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.LoginIdAndPassword;
import com.example.weatherdiary.dto.MemberSignUpParam;
import com.example.weatherdiary.exception.InvalidValueException;
import com.example.weatherdiary.exception.NotUniqueLoginIdException;

import java.util.Optional;

public interface MemberService {

    public Optional<String> findMemberLoginId(LoginIdAndPassword loginIdAndPassword);

    public void signUpMember(MemberSignUpParam memberSignUpParam) throws NotUniqueLoginIdException;

    public void checkLoginIdDuplicated(String loginId) throws NotUniqueLoginIdException;

    public void deleteMember(Member member, String password) throws InvalidValueException;
}
