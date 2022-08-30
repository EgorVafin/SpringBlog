package com.example.demo.controller.auth;

import com.example.demo.api.response.AuthCheckResponse;
import com.example.demo.api.response.CaptchaResponse;
import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.controller.auth.request.UserLoginRequest;
import com.example.demo.controller.auth.request.UserRegistrationRequest;
import com.example.demo.controller.auth.response.UserLoginResponse;
import com.example.demo.controller.auth.response.UserLogoutResponse;
import com.example.demo.dao.UserRepository;
import com.example.demo.service.CaptchaGenerator;
import com.example.demo.service.UserLoginService;
import com.example.demo.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final CaptchaGenerator captchaGenerator;
    private final UserRegistrationService userRegistrationService;
    private final UserRepository userRepository;
    private final UserLoginService userLoginService;

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
    public ResultErrorsResponse register(@RequestBody UserRegistrationRequest request) {

        return userRegistrationService.register(request);
    }

    @PostMapping("/login")
    public UserLoginResponse login(
            HttpServletRequest request,
            @RequestBody UserLoginRequest loginRequest) {

        try {
            request.login(loginRequest.getE_mail(), loginRequest.getPassword());
        } catch (ServletException e) {
            UserLoginResponse response = new UserLoginResponse();
            response.setResult(false);
            return response;
        }

        UserLoginResponse response = userLoginService.login(loginRequest);

        return response;
    }

    @GetMapping("/logout")
    public UserLogoutResponse logout(HttpServletRequest request) {

        try {
            request.logout();
        } catch (ServletException e) {
        }
        UserLogoutResponse userLogoutResponse = new UserLogoutResponse();

        userLogoutResponse.setResult(true);

        return userLogoutResponse;

    }
}
