package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.LoginIdAndPassword;

import java.util.Optional;

public interface MemberRepositoryCustom {

    public Optional<String> findMemberLoginId(LoginIdAndPassword loginIdAndPassword);
}
