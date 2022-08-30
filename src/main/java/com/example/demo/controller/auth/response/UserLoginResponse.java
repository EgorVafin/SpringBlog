package com.example.demo.controller.auth.response;

import lombok.Data;

@Data
public class UserLoginResponse {
    private boolean result;
    private UserLoginUserInfo user;
}
