package com.example.demo.controller;

import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/example")
@RequiredArgsConstructor
public class ExampleAuthController {

    @GetMapping("/user-info")
    public Map<String, String> exampleWithUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("id", String.valueOf(user.getId()));
        userInfo.put("email", user.getEmail());

        return userInfo;
    }
}
