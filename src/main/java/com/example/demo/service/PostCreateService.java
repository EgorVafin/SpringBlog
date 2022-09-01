package com.example.demo.service;

import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.controller.post.request.CreatePostRequest;
import com.example.demo.dao.PostRepository;
import com.example.demo.dao.TagRepository;
import com.example.demo.dao.TagToPostRepository;
import com.example.demo.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public ResultErrorsResponse createPost(CreatePostRequest request) {

        Map<String, String> errors = validate(request);

        if (!errors.isEmpty()) {
            ResultErrorsResponse response = new ResultErrorsResponse();
            response.setResult(false);
            response.setErrors(errors);

            return response;
        } else {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            Post post = new Post();

            //TODO тернарный возможен ли и нужен ли???
            if (request.getActive() == 1) {
                post.setActive(true);
            } else {
                post.setActive(false);
            }
            post.setModerationStatus(Status.NEW);

            //TODO доделать изменение времени
            post.setTime(new Timestamp(request.getTimestamp()));
            post.setTitle(request.getTitle());
            post.setText(request.getText());
            post.setViewCount(0);
            post.setUser(user);

            postRepository.save(post);

            //TODO оптимизировать поиск tags(flag убрать)
            List<Tag> tags = (List<Tag>) tagRepository.findAll();
            for (String tagFromRequest : request.getTags()) {

                boolean flagEqualsTags = false;
                TagToPost tagToPost = new TagToPost();
                tagToPost.setPost(post);

                for (Tag tagFromDB : tags) {
                    if (tagFromDB.getName().equals(tagFromRequest)) {
                        //old tag
                        flagEqualsTags = true;
                        tagToPost.setTag(tagFromDB);

                        tagToPostRepository.save(tagToPost);
                    }
                }
                if (!flagEqualsTags) {
                    //new tag
                    Tag tag = new Tag();
                    tag.setName(tagFromRequest);
                    tagRepository.save(tag);
                    tagToPost.setTag(tag);

                    tagToPostRepository.save(tagToPost);
                }
            }

            ResultErrorsResponse response = new ResultErrorsResponse();

            //TODO возвращает errors: NULL ... как избавиться?
            response.setResult(true);
            return response;
        }
    }

    private Map<String, String> validate(CreatePostRequest request) {
        //todo NULL достаточно ли меньше чем 3/50 ?
        Map<String, String> errors = new HashMap<>();
        if (request.getTitle().length() < 3) {
            errors.put("title", "Заголовок слишком короткий");
        }
        if (request.getText().length() < 50) {
            errors.put("text", "Текст публикации слишком короткий");
        }
        return errors;
    }
}
