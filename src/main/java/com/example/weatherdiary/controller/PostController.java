package com.example.weatherdiary.controller;

import com.example.weatherdiary.annotation.CurrentMember;
import com.example.weatherdiary.annotation.MemberLoginCheck;
import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.domain.Post;
import com.example.weatherdiary.dto.PostParam;
import com.example.weatherdiary.exception.NotExistPostException;
import com.example.weatherdiary.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @MemberLoginCheck
    @PostMapping
    public ResponseEntity<Void> uploadPost(@CurrentMember Member currentMember,
                                           @Valid PostParam postParam) {
        postService.uploadPost(currentMember, postParam);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @MemberLoginCheck
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        try {
            Post post = postService.getPostById(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (NotExistPostException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Post>> getPostsByLoginId() {
        return null;
    }

    public ResponseEntity<Void> updatePost() {
        return null;
    }

    public ResponseEntity<Void> deletePost() {
        return null;
    }

}
