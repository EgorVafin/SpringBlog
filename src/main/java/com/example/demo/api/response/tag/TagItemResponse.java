package com.example.demo.api.response.tag;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagItemResponse {

    private String name;

    private double weight;
}
