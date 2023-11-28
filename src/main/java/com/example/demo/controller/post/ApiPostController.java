package com.example.demo.controller.post;

import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.api.response.ResultResponse;
import com.example.demo.controller.post.request.AddCommentRequest;
import com.example.demo.controller.post.request.CreateUpdatePostRequest;
import com.example.demo.controller.post.request.ModerateRequest;
import com.example.demo.controller.post.request.PostLikeRequest;
import com.example.demo.controller.post.response.postDetail.RootPostResponse;
import com.example.demo.controller.post.response.postDetail.PostDetailResponse;
import com.example.demo.dao.PostRepository;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
public class ApiPostController {

    public enum Mode {
        recent, popular, best, early
    }

    public enum ModerationStatus {
        inactive, pending, declined, published
    }

    private final PostResponseProcessor responseProcessor;
    private final PostDetailService postDetailService;
    private final MyPostsService myPostsService;
    private final PostCreateService postCreateService;
    private final PostRepository postRepository;
    private final PostModerationService postModerationService;

    @RequestMapping("/api/post")
    @ResponseBody
    public RootPostResponse apiPost(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                    @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                    @RequestParam(value = "mode", required = false, defaultValue = "recent") Mode mode) {

        return responseProcessor.process(limit, offset, mode);
    }

    @RequestMapping("/api/post/search")
    @ResponseBody
    public RootPostResponse apiPostSearch(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                          @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                          @RequestParam(value = "query", required = false, defaultValue = "") String query) {

        return responseProcessor.searchPost(limit, offset, query);
    }

    @RequestMapping("/api/post/byDate")
    @ResponseBody
    public RootPostResponse apiPostSearchByDate(@RequestParam(value = "date", required = false, defaultValue = "") String date,
                                                @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset
    ) {

        return responseProcessor.searchByDate(date, limit, offset);
    }

    @RequestMapping("/api/post/byTag")
    @ResponseBody
    public RootPostResponse apiPostSearchByTag(@RequestParam(value = "tag", required = false, defaultValue = "") String tag,
                                               @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                               @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset
    ) {

        return responseProcessor.searchByTag(tag, limit, offset);
    }

    @RequestMapping("/api/post/{id}")
    @ResponseBody
    public ResponseEntity<PostDetailResponse> apiPostById(@PathVariable(value = "id") int id) {

        PostDetailResponse response = postDetailService.postById(id);

        if (response == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @RequestMapping("/api/post/my")
    @ResponseBody
    public RootPostResponse myPosts(@RequestParam(value = "offset", defaultValue = "0") @Min(0) int offset,
                                    @RequestParam(value = "limit", defaultValue = "1") @Min(1) int limit,
                                    @RequestParam(value = "status", required = true) ModerationStatus status) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return myPostsService.getMyPosts(limit, offset, status, user);
    }


    @PostMapping("/api/post")
    @ResponseBody
    public ResultErrorsResponse createPost(@RequestBody CreateUpdatePostRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return postCreateService.createPost(request, user);
    }

    @PutMapping("/api/post/{id}")
    @ResponseBody
    public ResponseEntity<ResultErrorsResponse> editPost(@RequestBody CreateUpdatePostRequest request,
                                                         @PathVariable(value = "id") int id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } else {
            ResultErrorsResponse result = postCreateService.updatePost(post.get(), request, user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping("/api/moderate")
    @ResponseBody
    public ResultErrorsResponse moderatePost(@RequestBody ModerateRequest request) {

        Optional<Post> post = postRepository.findById(request.getPost_id());
        if (!post.isPresent()) {
            return ResultErrorsResponse.errors(Map.of("error", "Post does not exist"));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return postModerationService.moderate(user, post.get(), request.getDecision());
    }

    @PostMapping("/api/post/like")
    @ResponseBody
    public ResultResponse likePost(@RequestBody PostLikeRequest request) {
        Optional<Post> post = postRepository.findById(request.getPost_id());
        if (!post.isPresent()) {
            return new ResultResponse(false);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();


    }


}
