package com.example.demo.service;

import com.example.demo.api.request.UserRegistrationRequest;
import com.example.demo.api.response.UserRegistrationResponse;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;

public UserRegistrationResponse register(UserRegistrationRequest request) {

    Map<String, String> errors = validate(request);
    if(!errors.isEmpty()) {

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setResult(false);
        response.setErrors(errors);

        return response;
    } else {

        registerUser(request);
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setResult(true);

        return response;
    }

}

    private void registerUser(UserRegistrationRequest request) {



    }

    private Map<String, String> validate(UserRegistrationRequest request) {

    Map<String,String> errors = new HashMap<>();

        Optional<User> user = userRepository.findOneByEmail(request.getE_mail());

if(user.isPresent()) {

    errors.put("email", "Этот e-mail уже зарегистрирован");
}


        return errors;
    }

}
