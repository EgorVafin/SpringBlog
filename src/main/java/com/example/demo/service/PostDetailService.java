package com.example.demo.service;

import com.example.demo.api.response.post.postDetail.PostDetailResponse;
import com.example.demo.dao.PostRepository;
import com.example.demo.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostDetailService {

    private final PostRepository repository;

    public PostDetailResponse postById(int id) {


        Optional<Post> postOptional = repository.findById(id);
        if(postOptional.isEmpty()) {
            return null;
        };

        Post post = postOptional.get();

        return null;

    }
}
