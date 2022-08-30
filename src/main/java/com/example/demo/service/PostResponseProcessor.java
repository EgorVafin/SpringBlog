package com.example.demo.service;

import com.example.demo.controller.post.ApiPostController;
import com.example.demo.controller.post.response.postDetail.RootPostResponse;
import com.example.demo.dao.PostProjection;
import com.example.demo.dao.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostResponseProcessor {

    private final PostRepository postRepository;
    private final PostProjectionConverter postProjectionConverter;

    public RootPostResponse process(int limit, int offset, ApiPostController.Mode mode) {

        Pageable pageable = createPageable(limit, offset);
        Map<ApiPostController.Mode, Tuple> modeOrders = Map.of(
                ApiPostController.Mode.recent, new Tuple("p.time", "desc"),
                ApiPostController.Mode.popular, new Tuple("commentsCount", "desc"),
                ApiPostController.Mode.best, new Tuple("likesCount", "desc"),
                ApiPostController.Mode.early, new Tuple("p.time", "asc")
        );

        Page<PostProjection> posts = postRepository.allPosts(pageable, modeOrders.get(mode).getFirst(), modeOrders.get(mode).getSecond());

        return postProjectionConverter.convert(posts);
    }

    public RootPostResponse searchByDate(String date, int limit, int offset) {

        Pageable pageable = createPageable(limit, offset);
        Page<PostProjection> posts = postRepository.postsByDate(pageable, date);

        return postProjectionConverter.convert(posts);
    }

    public RootPostResponse searchPost(int limit, int offset, String query) {

        Pageable pageable = createPageable(limit, offset);
        query = "%" + query + "%";
        Page<PostProjection> posts = postRepository.postsSearch(pageable, query);

        return postProjectionConverter.convert(posts);
    }

    public RootPostResponse searchByTag(String tag, Integer limit, Integer offset) {

        Pageable pageable = createPageable(limit, offset);
        Page<PostProjection> posts = postRepository.postsByTag(pageable, tag);

        return postProjectionConverter.convert(posts);
    }

    private Pageable createPageable(Integer limit, Integer offset) {

        int page = (int) Math.ceil((double) offset / limit);
        return PageRequest.of(page, limit);
    }

}
