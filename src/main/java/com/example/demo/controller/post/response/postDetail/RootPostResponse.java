package com.example.demo.controller.post.response.postDetail;

import com.example.demo.controller.post.response.PostResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RootPostResponse {

    private long count;
    private List<PostResponse> posts;

}
