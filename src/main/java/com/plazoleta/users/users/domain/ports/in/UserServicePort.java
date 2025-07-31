package com.plazoleta.users.users.domain.ports.in;

import com.plazoleta.users.users.domain.model.UserModel;

public interface UserServicePort {
    void registerOwner(UserModel userModel, String token);
    UserModel getUserById(Long id);
    void createEmployeeByOwner(UserModel employeeModel, Long ownerId, String role);
}
