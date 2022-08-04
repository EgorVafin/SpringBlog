package com.example.demo.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CaptchaConfig {

    @Value("${captcha.lifeTime}")
    private int lifeTime;
}
