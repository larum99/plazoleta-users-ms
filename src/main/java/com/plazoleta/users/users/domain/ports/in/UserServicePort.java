package com.plazoleta.users.users.domain.ports.in;

import com.plazoleta.users.users.domain.model.UserModel;

public interface UserServicePort {
    void registerUser(UserModel userModel);
    UserModel getUserById(Long id);

}
