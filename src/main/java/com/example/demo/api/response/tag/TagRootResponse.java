package com.example.demo.api.response.tag;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TagRootResponse {

    private List<TagItemResponse> tags;

}
