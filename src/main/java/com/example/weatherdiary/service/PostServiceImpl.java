package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.dto.PostParam;
import com.example.weatherdiary.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

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
}
