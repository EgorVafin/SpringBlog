package com.example.demo.controller;

import com.example.demo.api.response.AuthCheckResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    @RequestMapping("/check")
    @ResponseBody
    public AuthCheckResponse apiAuthCheck() {
        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        authCheckResponse.setResult(false);

        return authCheckResponse;
    }
}
