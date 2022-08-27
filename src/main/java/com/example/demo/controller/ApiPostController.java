package com.example.demo.controller;

import com.example.demo.api.response.SettingsResponse;
import com.example.demo.api.response.post.RootPostResponse;
import com.example.demo.api.response.post.postDetail.PostDetailResponse;
import com.example.demo.model.User;
import com.example.demo.service.MyPostsService;
import com.example.demo.service.PostDetailService;
import com.example.demo.service.PostResponseProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
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
    public RootPostResponse myPosts(@RequestParam(value = "offset", defaultValue = "0") int offset,
                        @RequestParam(value = "limit", defaultValue = "0") int limit,
                        @RequestParam(value = "status", required = true) ModerationStatus status) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return myPostsService.getMyPosts(limit, offset, status, user);


    }

}
