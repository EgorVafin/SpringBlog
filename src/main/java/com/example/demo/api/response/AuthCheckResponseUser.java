package com.example.demo.api.response;

import lombok.Data;

@Data
public class AuthCheckResponseUser {

    private int id;
    private String name;
    private String photo;
    private String email;
    private boolean moderation;
    private int moderationCount;
    private boolean settings;

}
