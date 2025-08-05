package com.plazoleta.users.users.domain.ports.out;

import com.plazoleta.users.users.domain.model.UserModel;

import java.util.Optional;

public interface UserPersistencePort {
    void saveUser(UserModel userModel);
    UserModel getUserByEmail(String email);
    UserModel getUserByDocument(String identityDocument);
    Optional<UserModel> getUserById(Long id);
    UserModel saveEmployee(UserModel userModel, Long restaurantId);
    void createEmployee(Long employeeId, Long restaurantId);
}
