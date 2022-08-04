package com.example.demo.api.response;

import lombok.Data;

import java.util.Map;

@Data
public class UserRegistrationResponse {

    private boolean result;
    private Map<String, String> errors;
}
