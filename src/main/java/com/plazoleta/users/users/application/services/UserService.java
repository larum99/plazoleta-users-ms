package com.plazoleta.users.users.application.services;

import com.plazoleta.users.users.application.dto.request.SaveUserRequest;
import com.plazoleta.users.users.application.dto.response.SaveUserResponse;

public interface UserService {
    SaveUserResponse save(SaveUserRequest request);
}
