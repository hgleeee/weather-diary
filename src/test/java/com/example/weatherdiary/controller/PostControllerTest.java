package com.example.weatherdiary.controller;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.dto.PostParam;
import com.example.weatherdiary.exception.NotExistMemberException;
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
import org.springframework.expression.AccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @DisplayName("일기 조회 실패 - 해당 일기 존재 x")
    void getPostWithFailure() throws Exception {
        // given
        doThrow(NotExistPostException.class).when(postService).getPostById(1L);

        // when-then
        mockMvc.perform(get("/post/1"))
                .andExpect(status().isNotFound());
        verify(postService).getPostById(1L);
    }

    @Test
    @DisplayName("로그인 ID로 해당 유저가 쓴 글 찾기 성공")
    void getPostsByLoginIdWithSuccess() throws Exception {
        // given
        when(postService.getPostsByLoginId("test")).thenReturn(new ArrayList<>());

        // when-then
        mockMvc.perform(get("/post?userId=test"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(postService).getPostsByLoginId("test");
    }

    @Test
    @DisplayName("로그인 ID로 해당 유저가 쓴 글 찾기 실패 - 로그인 ID와 일치하는 유저 없음")
    void getPostsByLoginIdWithFailure() throws Exception {
        // given
        doThrow(NotExistMemberException.class).when(postService).getPostsByLoginId("test");

        // when-then
        mockMvc.perform(get("/post?userId=test"))
                .andExpect(status().isNotFound());

        verify(postService).getPostsByLoginId("test");
    }

    @Test
    @DisplayName("일기 수정 성공")
    void updatePostWithSuccess() throws Exception {
        // given
        doNothing().when(postService).updatePost(any(Member.class), anyLong(), anyString());

        // when-then
        mockMvc.perform(patch("/post/1")
                        .param("content", "hello"))
                .andExpect(status().isOk());

        verify(postService).updatePost(any(Member.class), anyLong(), anyString());
    }

    @Test
    @DisplayName("일기 수정 실패 - 일기 고유 id가 올바르지 않음")
    void updatePostWithFailure1() throws Exception {
        // given
        doThrow(NotExistPostException.class).when(postService).updatePost(any(Member.class), anyLong(), anyString());

        // when-then
        mockMvc.perform(patch("/post/1")
                        .param("content", "hello"))
                .andExpect(status().isNotFound());

        verify(postService).updatePost(any(Member.class), anyLong(), anyString());
    }

    @Test
    @DisplayName("일기 수정 실패 - 작성자와 현재 사용자가 달라 수정 권한이 없음")
    void updatePostWithFailure2() throws Exception {
        // given
        doThrow(AccessException.class).when(postService).updatePost(any(Member.class), anyLong(), anyString());

        // when-then
        mockMvc.perform(patch("/post/1")
                        .param("content", "hello"))
                .andExpect(status().isUnauthorized());

        verify(postService).updatePost(any(Member.class), anyLong(), anyString());
    }

    @Test
    @DisplayName("일기 삭제 성공")
    void deletePostWithSuccess() throws Exception {
        // given
        doNothing().when(postService).deletePost(any(Member.class), anyLong());

        // when-then
        mockMvc.perform(delete("/post/1"))
                .andExpect(status().isOk());

        verify(postService).deletePost(any(Member.class), anyLong());
    }

    @Test
    @DisplayName("일기 삭제 실패 - 일기 고유 id가 올바르지 않음")
    void deletePostWithFailure1() throws Exception {
        // given
        doThrow(NotExistPostException.class).when(postService).deletePost(any(Member.class), anyLong());

        // when-then
        mockMvc.perform(delete("/post/1"))
                .andExpect(status().isNotFound());

        verify(postService).deletePost(any(Member.class), anyLong());
    }

    @Test
    @DisplayName("일기 삭제 실패 - 작성자와 현재 사용자가 달라 삭제 권한이 없음")
    void deletePostWithFailure2() throws Exception {
        // given
        doThrow(AccessException.class).when(postService).deletePost(any(Member.class), anyLong());

        // when-then
        mockMvc.perform(delete("/post/1"))
                .andExpect(status().isUnauthorized());

        verify(postService).deletePost(any(Member.class), anyLong());
    }
}