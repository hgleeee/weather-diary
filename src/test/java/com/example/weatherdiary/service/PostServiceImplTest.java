package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.dto.PostParam;
import com.example.weatherdiary.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Member member;

    @BeforeEach
    void beforeEach() {
        member = Member.builder()
                .loginId("test")
                .password("test")
                .name("lee")
                .email("test@test.com")
                .build();
    }

    @Test
    void uploadPost() {
        // given
        postService.uploadPost(member, new PostParam("hello everyone", "hello"));

        // when
        // then
        verify(postRepository).save(any(Post.class));
    }
}