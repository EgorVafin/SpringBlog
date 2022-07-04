package com.example.demo.controller;

import com.example.demo.dao.PostRepository;
import com.example.demo.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final PostRepository postRepository;

    @RequestMapping("/api/test/post/getById")
    @ResponseBody
    public Post getById() {

        Optional<Post> post = postRepository.findById(2);

        return post.get();
    }

}
