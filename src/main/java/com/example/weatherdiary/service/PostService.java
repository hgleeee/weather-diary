package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.dto.PostParam;
import com.example.weatherdiary.exception.NotExistMemberException;
import com.example.weatherdiary.exception.NotExistPostException;
import org.springframework.expression.AccessException;

import java.util.List;

public interface PostService {

    public void uploadPost(Member member, PostParam postParam);

    public Post getPostById(Long id) throws NotExistPostException;

    public List<Post> getPostsByLoginId(String loginId) throws NotExistMemberException;

    public void updatePost(Member member, Long postId, String content) throws NotExistPostException, AccessException;

    public void deletePost(Member member, Long postId) throws NotExistPostException, AccessException;
}
