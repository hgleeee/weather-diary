package com.example.weatherdiary.repository;

import com.example.weatherdiary.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
