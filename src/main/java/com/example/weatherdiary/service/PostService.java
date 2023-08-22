package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.dto.PostParam;

import java.util.List;

public interface PostService {

    public void uploadPost(Member member, PostParam postParam);

    public Post getPostById(Long id);

    public List<Post> getPostsByLoginId(String loginId);
}
