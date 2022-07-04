package com.example.demo.api.response.post.postDetail;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class PostCommentResponse {

    private int id;
    private long timestamp;
    private String text;
    private UserNamePhotoResponse user;

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public UserNamePhotoResponse getUser() {
        return user;
    }
}
