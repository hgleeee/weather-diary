package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.PostParam;

public interface PostService {

    public void uploadPost(Member member, PostParam postParam);
}
