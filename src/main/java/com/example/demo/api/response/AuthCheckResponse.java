package com.example.demo.api.response;

import lombok.Data;

@Data
public class AuthCheckResponse {

    private boolean result;
    private AuthCheckResponseUser user;

}
