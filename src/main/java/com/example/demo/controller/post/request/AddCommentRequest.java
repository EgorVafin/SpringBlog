package com.example.demo.controller.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCommentRequest {

    private String parent_id;
    private long post_id;
    private String text;
}
