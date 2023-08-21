package com.example.weatherdiary.service;

import com.example.weatherdiary.dto.LoginIdAndPassword;
import com.example.weatherdiary.dto.MemberSignUpParam;

import java.util.Optional;

public interface MemberService {

    public Optional<String> findMemberLoginId(LoginIdAndPassword loginIdAndPassword);

    public void signUpMember(MemberSignUpParam memberSignUpParam);

    public void checkLoginIdDuplicated(String loginId);
}
