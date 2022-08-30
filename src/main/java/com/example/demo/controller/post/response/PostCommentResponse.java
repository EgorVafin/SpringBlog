package com.example.demo.controller.post.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
@NoArgsConstructor
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
