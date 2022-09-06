package com.example.demo.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultErrorsResponse {

    private boolean result;
    private Map<String, String> errors;

    public static ResultErrorsResponse success() {

        return new ResultErrorsResponse(true, new HashMap<>());
    }

    public static ResultErrorsResponse errors(Map<String, String> errors) {

        return new ResultErrorsResponse(false, errors);
    }
}
