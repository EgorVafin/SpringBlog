package com.example.demo.api.response.post.postDetail;

import com.example.demo.api.response.UserNameResponse;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)

public class PostDetailResponse {

    private int id;
    private long timestamp;
    private boolean active;
    private UserNameResponse user;
    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private List<PostCommentResponse> comments;
    private List<String> tags;

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isActive() {
        return active;
    }

    public UserNameResponse getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public List<PostCommentResponse> getComments() {
        return comments;
    }

    public List<String> getTags() {
        return tags;
    }
}
