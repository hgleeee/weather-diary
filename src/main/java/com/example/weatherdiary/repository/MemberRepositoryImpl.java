package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.QMember;
import com.example.weatherdiary.dto.LoginIdAndPassword;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.weatherdiary.domain.QMember.*;

@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<String> findMemberLoginId(LoginIdAndPassword loginIdAndPassword) {
        return Optional.ofNullable(queryFactory.select(
                    member.loginId
                )
                .from(member)
                .where(member.loginId.eq(loginIdAndPassword.getLoginId())
                        .and(member.password.eq(loginIdAndPassword.getPassword())))
                .fetchOne());
    }
}
