package com.example.demo.service;

import com.example.demo.controller.auth.request.UserRegistrationRequest;
import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.dao.CaptchaRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.CaptchaCode;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final CaptchaRepository captchaRepository;
    private final PasswordEncoder passwordEncoder;

    public ResultErrorsResponse register(UserRegistrationRequest request) {

        Map<String, String> errors = validate(request);
        if (!errors.isEmpty()) {

            ResultErrorsResponse response = new ResultErrorsResponse();
            response.setResult(false);
            response.setErrors(errors);

            return response;
        } else {
            registerUser(request);
            ResultErrorsResponse response = new ResultErrorsResponse();
            response.setResult(true);

            registerUser(request);

            return response;
        }
    }

    private void registerUser(UserRegistrationRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getE_mail());
        user.setRegTime(new Timestamp(System.currentTimeMillis()));

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    private Map<String, String> validate(UserRegistrationRequest request) {

        Map<String, String> errors = new HashMap<>();

        Optional<User> user = userRepository.findOneByEmail(request.getE_mail());
        if (user.isPresent()) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }

        if (request.getName().trim().isEmpty()) {
            errors.put("name", "Имя указано неверно");
        }

        if (request.getPassword().length() < 6) {
            errors.put("password", "Пароль короче 6-ти символов");
        }

        Optional<CaptchaCode> captchaFromDb = captchaRepository.findBySecretCode(request.getCaptcha_secret());

        if (!captchaFromDb.isPresent()) {
            errors.put("captcha", "Код с картинки введён неверно");
        } else {
            if (!request.getCaptcha().equals(captchaFromDb.get().getCode())) {
                errors.put("captcha", "Код с картинки введён неверно");
            }
        }

        return errors;
    }
}
