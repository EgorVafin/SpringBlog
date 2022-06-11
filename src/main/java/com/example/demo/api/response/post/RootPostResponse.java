package com.example.demo.api.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RootPostResponse {

    private long count;
    private List<PostResponse> posts;

}
