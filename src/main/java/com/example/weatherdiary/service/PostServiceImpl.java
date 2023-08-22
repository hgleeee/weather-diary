package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.dto.PostParam;
import com.example.weatherdiary.exception.NotExistMemberException;
import com.example.weatherdiary.exception.NotExistPostException;
import com.example.weatherdiary.repository.MemberRepository;
import com.example.weatherdiary.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // TODO: 날씨 API 받아서 POST에 추가해주어야 함
    @Override
    public void uploadPost(Member member, PostParam postParam) {
        Post post = Post.builder()
                .title(postParam.getTitle())
                .content(postParam.getContent())
                .build();
        post.setMember(member);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    @Override
    public Post getPostById(Long id) throws NotExistPostException {
        return postRepository.findById(id).orElseThrow(() -> new NotExistPostException("해당 글이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> getPostsByLoginId(String loginId) throws NotExistMemberException {
        if (memberRepository.findByLoginId(loginId).isEmpty()) {
            throw new NotExistMemberException("해당 회원이 존재하지 않습니다.");
        }
        return postRepository.findByLoginId(loginId);
    }

    @Override
    public void updatePost(Member member, Long postId, String content) throws NotExistPostException, AccessException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotExistPostException("해당 글이 존재하지 않습니다."));
        if (!post.getMember().getId().equals(member.getId())) {
            throw new AccessException("해당 글의 수정 권한이 없습니다.");
        }
        post.setContent(content);
    }

    @Override
    public void deletePost(Member member, Long postId) throws NotExistPostException, AccessException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotExistPostException("해당 글이 존재하지 않습니다."));
        if (!post.getMember().getId().equals(member.getId())) {
            throw new AccessException("해당 글의 삭제 권한이 없습니다.");
        }
        postRepository.delete(post);
    }
}
