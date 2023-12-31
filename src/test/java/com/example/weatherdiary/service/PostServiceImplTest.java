package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.dto.PostParam;
import com.example.weatherdiary.exception.NotExistMemberException;
import com.example.weatherdiary.exception.NotExistPostException;
import com.example.weatherdiary.repository.MemberRepository;
import com.example.weatherdiary.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.expression.AccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Member member;

    @BeforeEach
    void beforeEach() {
        member = Member.builder()
                .id(1L)
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

    @Test
    @DisplayName("올바른 postId로 post 반환 성공")
    void getPostByIdWithSuccess() {
        // given
        Post post = Post.builder()
                .id(5L)
                .title("hello everyone")
                .content("hello")
                .createdDate(LocalDate.now())
                .build();
        when(postRepository.findById(5L)).thenReturn(Optional.of(post));
        // when
        Post getPost = postService.getPostById(5L);

        // then
        assertEquals(post, getPost);
    }

    @Test
    @DisplayName("존재하지 않는 postId로 post 반환 실패")
    void getPostByIdWithFailure() {
        // given
        when(postRepository.findById(6L)).thenReturn(Optional.empty());

        // when

        // then
        assertThrows(NotExistPostException.class, () -> postService.getPostById(6L),
                "해당 글이 존재하지 않습니다.");
    }

    @DisplayName("현재 존재하는 사용자 id를 통한 글 검색")
    @Test
    void getPostsByLoginIdTestWithSuccess() {
        // given
        Post post1 = Post.builder()
                .title("hello")
                .content("hi")
                .createdDate(LocalDate.now())
                .build();
        Post post2 = Post.builder()
                .title("hello2")
                .content("hi2")
                .createdDate(LocalDate.now())
                .build();

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        when(memberRepository.findByLoginId(member.getLoginId())).thenReturn(Optional.of(member));
        when(postRepository.findByLoginId(member.getLoginId())).thenReturn(posts);

        // when
        List<Post> targetPosts = postService.getPostsByLoginId(member.getLoginId());

        // then
        assertEquals(targetPosts, posts);
        verify(memberRepository).findByLoginId(member.getLoginId());
        verify(postRepository).findByLoginId(member.getLoginId());
    }

    @DisplayName("현재 존재하지 않는 사용자 id를 요청한 경우 NotExistMemberException을 던지며 실패")
    @Test
    void getPostsByLoginIdTestWithFailure() {
        when(memberRepository.findByLoginId("test2")).thenReturn(Optional.empty());

        assertThrows(NotExistMemberException.class, () -> {
            postService.getPostsByLoginId("test2");
        });

        verify(memberRepository).findByLoginId("test2");
        verify(postRepository, times(0)).findByLoginId("test2");
    }

    @Test
    @DisplayName("글 수정 성공")
    void updatePostByIdAndLoginIdWithSuccess() {
        // given
        when(postRepository.findById(5L)).thenReturn(Optional.of(Post.builder().member(member).build()));

        // when
        // then
        assertDoesNotThrow(() -> {
            postService.updatePost(member, 5L, "hello !!!");
        });
    }

    @Test
    @DisplayName("글 삭제 성공")
    void deletePostByIdAndLoginIdWithSuccess() {
        // given
        when(postRepository.findById(5L)).thenReturn(Optional.of(Post.builder().member(member).build()));

        // when
        // then
        assertDoesNotThrow(() -> {
            postService.deletePost(member, 5L);
        });
    }

    @Test
    @DisplayName("글 삭제 시 postId가 잘못되어 NotExistPostException 던지기")
    void deletePostByIdAndLoginIdWithIdFailure() {
        when(postRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(NotExistPostException.class,
                () -> postService.deletePost(member, 5L), "존재하지 않는 글에 대해 예외를 던져야 합니다.");
    }

    @Test
    @DisplayName("글 삭제 시 loginId가 권한이 없어 AccessException 던지기")
    void deletePostByIdAndLoginIdWithLoginIdFailure() {
        Member member2 = Member.builder()
                        .id(2L)
                        .loginId("test2")
                        .password("test2")
                        .build();
        when(postRepository.findById(5L)).thenReturn(Optional.of(Post.builder().member(member2).build()));
        assertThrows(AccessException.class,
                () -> postService.deletePost(member, 5L), "접근 권한이 없는 멤버는 예외를 던져야 합니다.");
    }
}