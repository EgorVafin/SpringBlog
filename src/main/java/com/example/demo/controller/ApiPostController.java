package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiPostController {

    @RequestMapping("/api/post/**")
    @ResponseBody
    public String apiPost() {

        return "Post";
    }
}
