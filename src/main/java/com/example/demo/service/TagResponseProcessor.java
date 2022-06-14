package com.example.demo.service;

import com.example.demo.api.response.tag.TagItemResponse;
import com.example.demo.api.response.tag.TagRootResponse;
import com.example.demo.dao.TagProjection;
import com.example.demo.dao.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagResponseProcessor {

    private final TagRepository tagRepository;

    public TagRootResponse process(String searchPhrase) {

        if (searchPhrase == null) {
            searchPhrase = "";
        }
        searchPhrase = "%" + searchPhrase;

        List<TagProjection> result = tagRepository.searchTags(searchPhrase);

        double maxWeight = findMaxWeight(result);
        double k = 1 / maxWeight;

        List<TagItemResponse> tagResponse = result.stream()
                .map(tag -> new TagItemResponse(tag.getName(), tag.getWeight() * k))
                .collect(Collectors.toList());

            return new TagRootResponse(tagResponse);
    }

    private double findMaxWeight(List<TagProjection> result) {

        double max = 0.0;
        for (TagProjection tag : result) {
            if (tag.getWeight() > max) {
                max = tag.getWeight();
            }
        }

        return max;
    }

}
