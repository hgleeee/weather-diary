package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    public Optional<Member> findByLoginId(String loginId);
}
