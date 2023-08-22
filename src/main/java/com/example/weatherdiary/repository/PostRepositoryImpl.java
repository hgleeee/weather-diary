package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.domain.QMember;
import com.example.weatherdiary.domain.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.weatherdiary.domain.QMember.*;
import static com.example.weatherdiary.domain.QPost.*;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findByLoginId(String loginId) {
        return queryFactory
                .selectFrom(post)
                .join(post.member, member)
                .where(post.member.loginId.eq(loginId))
                .fetch();
    }
}
