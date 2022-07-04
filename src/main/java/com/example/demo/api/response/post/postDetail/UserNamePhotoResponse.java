package com.example.demo.api.response.post.postDetail;

import com.example.demo.api.response.UserNameResponse;
import lombok.*;

@Getter
@Setter
public class UserNamePhotoResponse extends UserNameResponse {

    private String photo;

    public UserNamePhotoResponse(int id, String name, String photo) {
        super(id, name);
        this.photo = photo;
    }
}
