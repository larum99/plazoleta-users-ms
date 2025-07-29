package com.plazoleta.users.users.application.services.impl;

import com.plazoleta.users.commons.configurations.utils.Constants;
import com.plazoleta.users.users.application.dto.request.SaveUserRequest;
import com.plazoleta.users.users.application.dto.response.SaveUserResponse;
import com.plazoleta.users.users.application.mappers.UserDtoMapper;
import com.plazoleta.users.users.application.services.UserService;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.in.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserServicePort userServicePort;
    private final UserDtoMapper userDtoMapper;

    @Override
    public SaveUserResponse saveUser(SaveUserRequest request) {
        UserModel userModel = userDtoMapper.requestToModel(request);
        userServicePort.registerUser(userModel);
        return new SaveUserResponse(Constants.SAVE_USER_RESPONSE_MESSAGE, LocalDateTime.now());
    }
}
