package com.example.demo.service;

import com.example.demo.controller.post.response.postDetail.RootPostResponse;
import com.example.demo.controller.post.ApiPostController;
import com.example.demo.dao.PostProjection;
import com.example.demo.dao.PostRepository;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MyPostsService {
    private final PostRepository postRepository;
    private final PostProjectionConverter postProjectionConverter;

    public RootPostResponse getMyPosts(int limit, int offset, ApiPostController.ModerationStatus moderationStatus, User user) {

        Pageable pageable = createPageable(limit, offset);

        int userId = user.getId();
        Page<PostProjection> posts = switch (moderationStatus) {
            case inactive -> postRepository.userInactivePosts(pageable, userId);

            //TODO check 2
            case pending -> postRepository.userActivePostsWithStatus(pageable, userId, Status.NEW);
            case declined -> postRepository.userActivePostsWithStatus(pageable, userId, Status.DECLINED);
            case published -> postRepository.userActivePostsWithStatus(pageable, userId, Status.ACCEPTED);
            default -> throw new RuntimeException("Unknown moderation status");
        };
        return postProjectionConverter.convert(posts);
    }

    // !!!!! double code
    private Pageable createPageable(Integer limit, Integer offset) {
        int page = (int) Math.ceil((double) offset / limit);
        return PageRequest.of(page, limit);
    }
}


