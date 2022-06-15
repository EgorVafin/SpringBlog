package com.example.demo.controller;

import com.example.demo.api.response.post.RootPostResponse;
import com.example.demo.service.PostResponseProcessor;
import com.example.demo.service.PostSearchResponseProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiPostController {

    public enum Mode {
        recent, popular, best, early
    }

    private final PostResponseProcessor responseProcessor;
    private final PostSearchResponseProcessor postSearchResponseProcessor;

    @RequestMapping("/api/post")
    @ResponseBody
    public RootPostResponse apiPost(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                    @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                    @RequestParam(value = "mode", required = false, defaultValue = "recent") Mode mode) {

        return responseProcessor.process(limit, offset, mode);
    }

    @RequestMapping("/api/post/search")
    @ResponseBody
    public ResponseEntity<RootPostResponse> apiPostSearch(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                          @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                          @RequestParam(value = "query", required = false, defaultValue = "") String query) {

        return new ResponseEntity<>(postSearchResponseProcessor.process(limit, offset, query), HttpStatus.OK);
    }
}
