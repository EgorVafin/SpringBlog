package com.example.demo.controller.post;

import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.controller.post.request.AddCommentRequest;
import com.example.demo.dao.CommentRepository;
import com.example.demo.dao.PostRepository;
import com.example.demo.model.Post;
import com.example.demo.model.PostComment;
import com.example.demo.service.AddCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ApiCommentController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AddCommentService addCommentService;


    @PostMapping("/api/comment")
    @ResponseBody
    public ResponseEntity<ResultErrorsResponse> addComment(@RequestBody AddCommentRequest request) {

        Optional<Post> post = postRepository.findById((int) request.getPost_id());
        if (!post.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Optional<PostComment> postComment = Optional.empty();
        if (!request.getParent_id().isEmpty()) {
            postComment = commentRepository.findById(Integer.parseInt(request.getParent_id()));
            if (!postComment.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }

        addCommentService.createComment(post.get(), postComment.orElse(null), request.getText());


//        if (response == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        } else {
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }

        return null;
    }

}
