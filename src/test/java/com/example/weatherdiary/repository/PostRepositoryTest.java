package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("JpaRepository -> save()")
    void save() {
        // given
        Post post = Post.builder()
                .title("hi")
                .content("hello")
                .createdDate(LocalDate.now())
                .build();

        // when
        Post savedPost = postRepository.save(post);

        // then
        assertEquals("hi", savedPost.getTitle());
        assertEquals("hello", savedPost.getContent());
    }

    @Test
    @DisplayName("JpaRepository -> findById()")
    void findById() {
        // given
        LocalDate localDate = LocalDate.now();
        Post savedPost1 = postRepository.save(Post.builder()
                .title("hi1")
                .content("hello1")
                .createdDate(localDate)
                .build());
        Post savedPost2 = postRepository.save(Post.builder()
                .title("hi2")
                .content("hello2")
                .createdDate(localDate)
                .build());

        // when
        Post findPost1 = postRepository.findById(savedPost1.getId()).orElseThrow(() -> new IllegalArgumentException());
        Post findPost2 = postRepository.findById(savedPost2.getId()).orElseThrow(() -> new IllegalArgumentException());

        // then
        Assertions.assertEquals(2, postRepository.count());
        findById_validation(savedPost1, findPost1);
        findById_validation(savedPost2, findPost2);
    }

    private void findById_validation(Post savedPost, Post findPost) {
        Assertions.assertEquals(savedPost.getTitle(), findPost.getTitle());
        Assertions.assertEquals(savedPost.getContent(), findPost.getContent());
        Assertions.assertEquals(savedPost.getCreatedDate(), findPost.getCreatedDate());
    }

    @Test
    @DisplayName("JpaRepository -> delete()")
    void delete() {
        // given
        Post savedPost = postRepository.save(Post.builder()
                .title("hi")
                .content("hello")
                .createdDate(LocalDate.now())
                .build());

        // when
        postRepository.delete(postRepository.findById(savedPost.getId()).orElseThrow(() -> new RuntimeException()));

        // then
        assertEquals(Optional.empty(), postRepository.findById(savedPost.getId()));
    }
}