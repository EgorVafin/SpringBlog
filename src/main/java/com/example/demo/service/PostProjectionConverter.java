package com.example.demo.service;

import com.example.demo.controller.post.response.PostResponse;
import com.example.demo.api.response.UserNameResponse;
import com.example.demo.controller.post.response.postDetail.RootPostResponse;
import com.example.demo.dao.PostProjection;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostProjectionConverter {

    public RootPostResponse convert(Page<PostProjection> posts) {

        List<PostResponse> postList = posts.stream().map(post -> new PostResponse()
                        .id(post.getId())
                        .timestamp(post.getPostTimestamp())
                        .user(new UserNameResponse(post.getUserId(), post.getUserName()))
                        .title(post.getTitle())
                        .announce(processAnnounce(post.getText()))
                        .likeCount(post.getLikesCount())
                        .dislikeCount(post.getDislikesCount())
                        .commentCount(post.getCommentsCount())
                        .viewCount(post.getViewCount()))
                .collect(Collectors.toList());

        return new RootPostResponse(posts.getTotalElements(), postList);
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
