package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
