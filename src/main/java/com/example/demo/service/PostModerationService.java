package com.example.demo.service;

import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.dao.PostRepository;
import com.example.demo.model.Post;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostModerationService {

    private final PostRepository postRepository;

    public ResultErrorsResponse moderate(User user, Post post, String decision) {

        if (user.getIsModerator() != 1) {
            return ResultErrorsResponse.errors(Map.of("error", "User in not moderator"));
        }

        Status moderationStatus;
        switch (decision) {
            case ("accept"):
                moderationStatus = Status.ACCEPTED;
                break;
            case ("decline"):
                moderationStatus = Status.DECLINED;
                break;
            default:
                return ResultErrorsResponse.errors(Map.of("error", "Incorrect moderation status"));
        }

        post.setModerationStatus(moderationStatus);

        postRepository.save(post);

        return ResultErrorsResponse.success();
    }

}
