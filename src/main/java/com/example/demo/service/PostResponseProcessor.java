package com.example.demo.service;

import com.example.demo.api.response.post.PostResponse;
import com.example.demo.api.response.post.PostUserResponse;
import com.example.demo.api.response.post.RootPostResponse;
import com.example.demo.controller.ApiPostController;
import com.example.demo.dao.PostProjection;
import com.example.demo.dao.PostRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostResponseProcessor {

    private final PostRepository postRepository;
    private final PostProjectionConverter postProjectionConverter;

    public RootPostResponse process(int limit, int offset, ApiPostController.Mode mode) {

        int page = (int) Math.ceil((double) offset / limit);
        Pageable pageable = PageRequest.of(page, limit);

        Map<ApiPostController.Mode, Tuple> modeOrders = Map.of(
                ApiPostController.Mode.recent, new Tuple("p.time", "desc"),
                ApiPostController.Mode.popular, new Tuple("commentsCount", "desc"),
                ApiPostController.Mode.best, new Tuple("likesCount", "desc"),
                ApiPostController.Mode.early, new Tuple("p.time", "asc")
        );

        Page<PostProjection> posts = postRepository.allPosts(pageable, modeOrders.get(mode).getFirst(), modeOrders.get(mode).getSecond());

        return postProjectionConverter.convert(posts);
    }

}
