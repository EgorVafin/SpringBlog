package com.example.demo.controller;

import com.example.demo.dao.PostRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DefaultController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public DefaultController(@Autowired PostRepository postRepository,
                             @Autowired UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/")
    public String index(Model model) {
        List<Post> posts = postRepository.findAll();
        List<User> users = userRepository.findAll();

        model.addAttribute("posts", posts);
        model.addAttribute("users", users);

        return "index";
    }
}
