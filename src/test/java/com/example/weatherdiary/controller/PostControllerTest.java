package com.example.weatherdiary.controller;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.dto.PostParam;
import com.example.weatherdiary.exception.NotExistPostException;
import com.example.weatherdiary.repository.MemberRepository;
import com.example.weatherdiary.resolver.CurrentMemberArgumentResolver;
import com.example.weatherdiary.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    CurrentMemberArgumentResolver currentMemberArgumentResolver;

    @MockBean
    LoginController loginController;

    Post post;

    @BeforeEach
    void beforeEach() {
        post = Post.builder()
                .id(1L)
                .title("hello everyone")
                .content("hi")
                .createdDate(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("일기 쓰기")
    void uploadPostWithSuccess() throws Exception {
        // given
        doNothing().when(postService).uploadPost(any(Member.class), any(PostParam.class));
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("title", "hello everyone!");
        param.add("content", "hi");

        // when-then
        mockMvc.perform(post("/post")
                .params(param)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(postService).uploadPost(any(Member.class), any(PostParam.class));
    }

    @Test
    @DisplayName("일기 조회 성공")
    void getPostWithSuccess() throws Exception {
        // given
        when(postService.getPostById(1L)).thenReturn(post);

        // when-then
        mockMvc.perform(get("/post/1"))
                .andExpect(status().isOk());
        verify(postService).getPostById(1L);
    }

    @Test
    @DisplayName("일기 조회 실패")
    void getPostWithFailure() throws Exception {
        // given
        doThrow(NotExistPostException.class).when(postService).getPostById(1L);

        // when-then
        mockMvc.perform(get("/post/1"))
                .andExpect(status().isNotFound());
        verify(postService).getPostById(1L);
    }

    @Test
    void getPostsByLoginId() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePost() {
    }
}