package com.example.weatherdiary.domain;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "CLOB")
    private String content;

    @ManyToOne
    private User user;

    private Date createdDate;
}
