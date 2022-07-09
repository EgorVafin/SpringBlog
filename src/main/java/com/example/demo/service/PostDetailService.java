package com.example.demo.service;

import com.example.demo.api.response.UserNameResponse;
import com.example.demo.api.response.post.postDetail.PostCommentResponse;
import com.example.demo.api.response.post.postDetail.PostDetailResponse;
import com.example.demo.api.response.post.postDetail.UserNamePhotoResponse;
import com.example.demo.dao.PostLikesDislikesCount;
import com.example.demo.dao.PostRepository;
import com.example.demo.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostDetailService {

    private final PostRepository repository;

    public PostDetailResponse postById(int id) {

        Optional<Post> postOptional = repository.findById(id);
        if (postOptional.isEmpty()) {
            return null;
        }

        Post post = postOptional.get();

        if (!post.canDisplay()) {

            return null;
        }

        PostLikesDislikesCount likeDislikeCount = repository.postLikeDislikeCount(id);

        PostDetailResponse response = new PostDetailResponse();
        response.id(post.getId())
                .timestamp(post.getTime().getTime())
                .active(post.isActive())
                .user(new UserNameResponse(post.getUser().getId(), post.getUser().getName()))
                .title(post.getTitle())
                .text(post.getText())
                .likeCount(likeDislikeCount.getLikeCount())
                .dislikeCount(likeDislikeCount.getDislikeCount())
                .viewCount(post.getViewCount())
                .comments(convertComments(post))
                .tags(post.getTags().stream().map(tagToPost -> tagToPost.getTag().getName()).collect(Collectors.toList()));

        post.setViewCount(post.getViewCount() + 1);
        repository.save(post);

        return response;
    }

    private List<PostCommentResponse> convertComments(Post post) {

        return post.getComments().stream().map(comment -> new PostCommentResponse()
                .id(comment.getId())
                .timestamp(comment.getTime().getTime())
                .text(comment.getText())
                .user(new UserNamePhotoResponse(comment.getUser().getId(), comment.getUser().getName(), comment.getUser().getPhoto())
                )).collect(Collectors.toList());

    }
}
