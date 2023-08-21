package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    public List<Member> findByLoginId(String loginId);
}
