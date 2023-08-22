package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findByLoginId(String loginId);
}
