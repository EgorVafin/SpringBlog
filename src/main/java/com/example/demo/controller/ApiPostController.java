package com.example.demo.controller;

import com.example.demo.dao.PostRepository;
import com.example.demo.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ApiPostController {

    private final PostRepository postRepository;

    @RequestMapping("/api/post/**")
    @ResponseBody
    public Page<Post> apiPost(@RequestParam(value = "title", required = true) String title) {

        Pageable pageable = PageRequest.of(0, 20);
        Page<Post> result = postRepository.search(title, pageable);

        return result;
//        ResponseStub responseStub = new ResponseStub("test message");
//        return responseStub;
    }
}
