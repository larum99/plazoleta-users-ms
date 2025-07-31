package com.plazoleta.users.users.application.services;

import com.plazoleta.users.users.application.dto.request.SaveUserRequest;
import com.plazoleta.users.users.application.dto.response.SaveUserResponse;

public interface UserService {
    SaveUserResponse saveOwnerByAdmin(SaveUserRequest request, String token);
    SaveUserResponse saveEmployeeByOwner(SaveUserRequest request, String token);
    SaveUserResponse saveClient(SaveUserRequest request);
}
