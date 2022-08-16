package com.example.demo.service;

import com.example.demo.api.request.UserLoginRequest;
import com.example.demo.api.response.user.login.UserLoginResponse;
import com.example.demo.api.response.user.login.UserLoginUserInfo;
import com.example.demo.dao.PostRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import com.example.demo.utils.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserLoginService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserLoginResponse login(UserLoginRequest loginRequest) {
        Optional<User> user = userRepository.findOneByEmail(loginRequest.getE_mail());

        if (!user.isPresent()) {
            UserLoginResponse response = new UserLoginResponse();
            response.setResult(false);
            return response;
        }

        UserLoginResponse response = new UserLoginResponse();
        UserLoginUserInfo userInfo = new UserLoginUserInfo();

        userInfo.setId(user.get().getId());
        userInfo.setName(user.get().getName());
        userInfo.setPhoto(user.get().getPhoto());
        userInfo.setEmail(user.get().getEmail());
        userInfo.setModeration(ConvertUtils.intToBool(user.get().getIsModerator()));
        userInfo.setModerationCount(postRepository.findPostCountForModeration(user.get().getId()));
        userInfo.setSettings(ConvertUtils.intToBool(user.get().getIsModerator()));

        response.setResult(true);
        response.setUser(userInfo);

        return response;
    }
}
