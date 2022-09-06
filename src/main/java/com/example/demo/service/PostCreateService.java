package com.example.demo.service;

import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.controller.post.request.CreateUpdatePostRequest;
import com.example.demo.dao.PostRepository;
import com.example.demo.dao.TagRepository;
import com.example.demo.dao.TagToPostRepository;
import com.example.demo.model.*;
import com.example.demo.utils.ConvertUtils;
import com.example.demo.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostCreateService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final TagToPostRepository tagToPostRepository;

    @Transactional
    public ResultErrorsResponse createPost(CreateUpdatePostRequest request, User user) {

        Map<String, String> errors = validate(request);

        if (!errors.isEmpty()) {
            return ResultErrorsResponse.errors(errors);
        }

        Post post = new Post();
        post.setModerationStatus(Status.NEW);
        post.setViewCount(0);
        post.setUser(user);

        fillPost(request, post, user);

        return ResultErrorsResponse.success();
    }

    public ResultErrorsResponse updatePost(Post post, CreateUpdatePostRequest request, User user) {

        Map<String, String> errors = validate(request);

        if (!errors.isEmpty()) {
            return ResultErrorsResponse.errors(errors);
        }
        post.setModerationStatus(Status.NEW);
        fillPost(request, post, user);

        return ResultErrorsResponse.success();
    }

    private Map<String, String> validate(CreateUpdatePostRequest request) {

        Map<String, String> errors = new HashMap<>();

        if (request.getTitle() == null) {
            errors.put("title", "Заголовок не установлен");
        } else if (request.getTitle().length() < 3) {
            errors.put("title", "Заголовок слишком короткий");
        }
        if (request.getText() == null) {
            errors.put("text", "Текст не установлен");
        } else if (request.getText().length() < 50) {
            errors.put("text", "Текст публикации слишком короткий");
        }

        return errors;
    }

    private void fillPost(CreateUpdatePostRequest request, Post post, User user) {

        post.setActive(ConvertUtils.intToBool(request.getActive()));

        post.setTime(TimeUtils.changeToCurrentIfOld(new Timestamp(request.getTimestamp())));
        post.setTitle(request.getTitle());
        post.setText(request.getText());

        postRepository.save(post);
        fillTags(request, post);
    }

    private void fillTags(CreateUpdatePostRequest request, Post post) {

        HashMap<String, Tag> tags = new HashMap();
        List<Tag> existingTags = tagRepository.findByNameIn(request.getTags());

        for (Tag tag : existingTags) {
            tags.put(tag.getName(), tag);
        }

        for (String tag : request.getTags()) {
            if (!tags.containsKey(tag)) {
                Tag newTag = new Tag();
                newTag.setName(tag);
                tagRepository.save(newTag);
                tags.put(tag, newTag);
            }
        }

        for (Tag tag : tags.values()) {
            TagToPost tagToPost = new TagToPost();
            tagToPost.setPost(post);
            tagToPost.setTag(tag);
            tagToPostRepository.save(tagToPost);
        }
    }
}
