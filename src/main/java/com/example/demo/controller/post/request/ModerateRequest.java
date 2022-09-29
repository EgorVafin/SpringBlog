package com.example.demo.controller.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModerateRequest {

    private int post_id;
    private String decision;
}
