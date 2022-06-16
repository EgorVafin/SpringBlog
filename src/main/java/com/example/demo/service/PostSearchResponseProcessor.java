package com.example.demo.service;

import com.example.demo.api.response.post.RootPostResponse;
import com.example.demo.dao.PostProjection;
import com.example.demo.dao.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSearchResponseProcessor {

    private final PostRepository postRepository;
    private final PostProjectionConverter postProjectionConverter;

    public RootPostResponse process(int limit, int offset, String query) {

        int page = (int) Math.ceil((double) offset / limit);
        Pageable pageable = PageRequest.of(page, limit);

        query = "%" + query + "%";

        Page<PostProjection> posts = postRepository.postsSearch(pageable, query);

        return postProjectionConverter.convert(posts);

    }

}
