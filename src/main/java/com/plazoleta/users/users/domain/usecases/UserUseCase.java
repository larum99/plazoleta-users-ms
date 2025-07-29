package com.plazoleta.users.users.domain.usecases;

import com.plazoleta.users.users.domain.exceptions.*;
import com.plazoleta.users.users.domain.helpers.UserHelper;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.in.UserServicePort;
import com.plazoleta.users.users.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.users.users.domain.ports.out.RolePersistencePort;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;

public class UserUseCase implements UserServicePort {
    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final RolePersistencePort rolePersistencePort;

    public UserUseCase(UserPersistencePort userPersistencePort,
                       PasswordEncoderPort passwordEncoderPort,
                       RolePersistencePort rolePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void registerUser(UserModel userModel) {
        UserHelper.normalizeUser(userModel);

        UserHelper.validateMandatoryFields(userModel);
        UserHelper.validateEmail(userModel.getEmail());
        UserHelper.validatePhone(userModel.getPhoneNumber());
        UserHelper.validateDocument(userModel.getIdentityDocument());
        UserHelper.validateAge(userModel.getBirthDate());

        UserHelper.checkIfDocumentExists(userModel.getIdentityDocument(), userPersistencePort);
        UserHelper.checkIfEmailExists(userModel.getEmail(), userPersistencePort);

        UserHelper.validateAndAssignRole(userModel, rolePersistencePort);

        String encryptedPassword = passwordEncoderPort.encode(userModel.getPassword());
        userModel.setPassword(encryptedPassword);

        userPersistencePort.saveUser(userModel);
    }

    @Override
    public UserModel getUserById(Long id) {
        return userPersistencePort.getUserById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}