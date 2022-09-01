package com.example.demo.service;

import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.controller.post.request.CreatePostRequest;
import com.example.demo.dao.PostRepository;
import com.example.demo.dao.TagRepository;
import com.example.demo.dao.TagToPostRepository;
import com.example.demo.model.*;
import com.example.demo.utils.ConvertUtils;
import com.example.demo.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResultErrorsResponse createPost(CreatePostRequest request, User user) {

        Map<String, String> errors = validate(request);

        if (!errors.isEmpty()) {
            ResultErrorsResponse response = new ResultErrorsResponse();
            response.setResult(false);
            response.setErrors(errors);

            return response;
        } else {

            Post post = new Post();

            post.setActive(ConvertUtils.intToBool(request.getActive()));
            post.setModerationStatus(Status.NEW);

            post.setTime(TimeUtils.changeToCurrentIfOld(new Timestamp(request.getTimestamp())));

            post.setTitle(request.getTitle());
            post.setText(request.getText());
            post.setViewCount(0);
            post.setUser(user);

            postRepository.save(post);

            HashMap<String, Tag> tags = new HashMap();
            List<Tag> existingTags = tagRepository.findByNameIn(request.getTags());

            for (Tag tag : existingTags) {
                tags.put(tag.getName(), tag);

            }

            for (String tag : request.getTags()) {
                if (!tags.containsKey(tag)) {
                    Tag newTag = new Tag;
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


//            List<Tag> tags = (List<Tag>) tagRepository.findAll();
//            for (String tagFromRequest : request.getTags()) {
//
//                boolean flagEqualsTags = false;
//                TagToPost tagToPost = new TagToPost();
//                tagToPost.setPost(post);
//
//                for (Tag tagFromDB : tags) {
//                    if (tagFromDB.getName().equals(tagFromRequest)) {
//                        //old tag
//                        flagEqualsTags = true;
//                        tagToPost.setTag(tagFromDB);
//
//                        tagToPostRepository.save(tagToPost);
//                    }
//                }
//                if (!flagEqualsTags) {
//                    //new tag
//                    Tag tag = new Tag();
//                    tag.setName(tagFromRequest);
//                    tagRepository.save(tag);
//                    tagToPost.setTag(tag);
//
//                    tagToPostRepository.save(tagToPost);
//                }
//            }

            ResultErrorsResponse response = new ResultErrorsResponse();

            //TODO возвращает errors: NULL ... как избавиться?
            response.setResult(true);
            return response;
        }
    }

    private Map<String, String> validate(CreatePostRequest request) {

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
}
