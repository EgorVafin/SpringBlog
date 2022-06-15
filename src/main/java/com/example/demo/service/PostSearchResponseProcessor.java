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
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSearchResponseProcessor {

    private final PostRepository postRepository;

    public RootPostResponse process(int limit, int offset, String query) {

        int page = (int) Math.ceil((double) offset / limit);
        Pageable pageable = PageRequest.of(page, limit);

        query = "%" + query + "%";

        Page<PostProjection> posts = postRepository.postsSearch(pageable, query);

        List<PostResponse> postList = new ArrayList<>();
        for (PostProjection post : posts) {
            PostResponse postResponse = new PostResponse();

            postResponse.setId(post.getId());
            postResponse.setTimestamp(post.getPostTimestamp());
            postResponse.setUser(new PostUserResponse(post.getUserId(), post.getUserName()));
            postResponse.setTitle(post.getTitle());
            postResponse.setAnnounce(processAnnounce(post.getText()));
            postResponse.setLikeCount(post.getLikesCount());
            postResponse.setDislikeCount(post.getDislikesCount());
            postResponse.setCommentCount(post.getCommentsCount());
            postResponse.setViewCount(post.getViewCount());

            postList.add(postResponse);
        }

        RootPostResponse response = new RootPostResponse(posts.getTotalElements(), postList);

        return response;

    }


    private String processAnnounce(String text) {

        String clearText = Jsoup.parse(text).text();
        if (clearText.length() > 150) {
            clearText = clearText.substring(0, 150);
        }
        clearText = clearText + "...";

        return clearText;
    }
}
