package com.example.demo.service;

import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.controller.post.request.CreatePostRequest;
import com.example.demo.dao.PostRepository;
import com.example.demo.dao.TagRepository;
import com.example.demo.dao.TagToPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostEditService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final TagToPostRepository tagToPostRepository;

    public ResultErrorsResponse editPostById(CreatePostRequest request) {



        return null;
    }

}
