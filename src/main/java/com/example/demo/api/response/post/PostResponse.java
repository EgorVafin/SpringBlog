package com.example.demo.api.response.post;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
//@Accessors(fluent = true)
public class PostResponse {

    private int id;
    private long timestamp;
    private PostUserResponse user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
}
