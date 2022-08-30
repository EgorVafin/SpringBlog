package com.example.demo.service;

import com.example.demo.dao.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCreateService {

    private final PostRepository repository;




}
