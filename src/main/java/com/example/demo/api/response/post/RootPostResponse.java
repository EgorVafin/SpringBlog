package com.example.demo.api.response.post;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RootPostResponse {
    private int count;
    private List<PostResponse> posts;

    public RootPostResponse() {
        posts = new ArrayList<>();
    }
}
