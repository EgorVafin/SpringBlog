package com.example.demo.api.response.user.login;

import lombok.Data;

@Data
public class UserLoginUserInfo {
    private int id;
    private String name;
    private String photo;
    private String email;
    private boolean moderation;
    private int moderationCount;
    private boolean settings;
}
