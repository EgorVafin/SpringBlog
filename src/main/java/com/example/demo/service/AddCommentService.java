package com.example.demo.service;

import com.example.demo.controller.post.request.AddCommentRequest;
import com.example.demo.model.Post;
import com.example.demo.model.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommentService {

    public void createComment (Post post, PostComment comment, String text) {


    }
}
