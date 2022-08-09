package com.example.demo.controller;

import com.example.demo.api.request.UserRegistrationRequest;
import com.example.demo.api.response.AuthCheckResponse;
import com.example.demo.api.response.CaptchaResponse;
import com.example.demo.api.response.UserRegistrationResponse;
import com.example.demo.service.CaptchaGenerator;
import com.example.demo.service.UserRegistrationService;
import com.github.cage.GCage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final CaptchaGenerator captchaGenerator;
    private final UserRegistrationService userRegistrationService;

    @RequestMapping("/check")
    @ResponseBody
    public AuthCheckResponse apiAuthCheck() {
        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        authCheckResponse.setResult(false);

        return authCheckResponse;
    }

    @RequestMapping("/captcha")
    @ResponseBody
    public CaptchaResponse captchaResponse() {

        return captchaGenerator.createCaptcha();
    }

    @PostMapping("/register")
    @ResponseBody
    public UserRegistrationResponse register(@RequestBody UserRegistrationRequest request) {

        return userRegistrationService.register(request);
    }
}
