package com.example.demo.api.response.user.login;

import lombok.Data;

@Data
public class UserLoginResponse {
    private boolean result;
    private UserLoginUserInfo user;
}
