package com.example.demo.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CaptchaResponse {

private String secret;
private String image;
}
