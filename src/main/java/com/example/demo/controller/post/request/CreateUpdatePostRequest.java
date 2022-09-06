package com.example.demo.controller.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateUpdatePostRequest {

    private long timestamp;
    private short active;
    private String title;
    private List<String> tags;
    private String text;
}
