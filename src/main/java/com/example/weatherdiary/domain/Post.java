package com.example.weatherdiary.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// TODO: 날씨 의존성 추가 필요
@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "CLOB")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "createdDate")
    private LocalDate createdDate;

    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }
}
