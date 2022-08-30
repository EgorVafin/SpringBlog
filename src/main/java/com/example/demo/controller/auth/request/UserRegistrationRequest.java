package com.example.demo.controller.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationRequest {

    private String e_mail;
    private String name;
    private String password;
    private String captcha;
    private String captcha_secret;

}
