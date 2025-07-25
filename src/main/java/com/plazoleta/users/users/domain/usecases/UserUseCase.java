package com.plazoleta.users.users.domain.usecases;

import com.plazoleta.users.users.domain.exceptions.*;
import com.plazoleta.users.users.domain.model.RoleModel;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.in.UserServicePort;
import com.plazoleta.users.users.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.users.users.domain.ports.out.RolePersistencePort;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;
import com.plazoleta.users.users.domain.utils.DomainConstants;
import com.plazoleta.users.users.domain.utils.RegexUtils;

import java.time.LocalDate;
import java.time.Period;

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
        normalizeStringFields(userModel);

        validateMandatoryFields(userModel);
        validateEmail(userModel.getEmail());
        validatePhone(userModel.getPhoneNumber());
        validateDocument(userModel.getIdentityDocument());
        validateAge(userModel.getBirthDate());

        checkIfDocumentAlreadyExists(userModel.getIdentityDocument());
        checkIfUserAlreadyExists(userModel.getEmail());

        String encryptedPassword = passwordEncoderPort.encode(userModel.getPassword());
        userModel.setPassword(encryptedPassword);

        RoleModel role;
        if (userModel.getRole() != null && userModel.getRole().getId() != null) {
            role = rolePersistencePort.findById(userModel.getRole().getId());
            if (role == null) throw new InvalidRoleException();
        } else {
            role = validateAndConvertRole(DomainConstants.OWNER);
        }
        userModel.setRole(role);

        userPersistencePort.saveUser(userModel);
    }

    private RoleModel validateAndConvertRole(String roleName) {
        RoleModel role = rolePersistencePort.findByName(roleName.trim().toUpperCase());
        if (role == null) {
            throw new InvalidRoleException();
        }
        return role;
    }

    private void normalizeStringFields(UserModel userModel) {
        userModel.setFirstName(safeTrim(userModel.getFirstName()));
        userModel.setLastName(safeTrim(userModel.getLastName()));
        userModel.setIdentityDocument(safeTrim(userModel.getIdentityDocument()));
        userModel.setPhoneNumber(safeTrim(userModel.getPhoneNumber()));
        userModel.setEmail(safeTrim(userModel.getEmail()));
        userModel.setPassword(safeTrim(userModel.getPassword()));
    }

    private String safeTrim(String value) {
        return value == null ? null : value.trim();
    }

    private void validateMandatoryFields(UserModel userModel) {
        if (isBlank(userModel.getFirstName())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_FIRSTNAME);
        if (isBlank(userModel.getLastName())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_LASTNAME);
        if (isBlank(userModel.getIdentityDocument())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DOCUMENT);
        if (isBlank(userModel.getPhoneNumber())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_PHONE);
        if (userModel.getBirthDate() == null) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_BIRTHDATE);
        if (isBlank(userModel.getEmail())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_EMAIL);
        if (isBlank(userModel.getPassword())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_PASSWORD);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void validateEmail(String email) {
        if (!RegexUtils.EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    private void validatePhone(String phone) {
        if (phone.length() > DomainConstants.PHONE_MAX_LENGTH ||
                !RegexUtils.PHONE_PATTERN.matcher(phone).matches()) {
            throw new InvalidPhoneException();
        }
    }

    private void validateDocument(String document) {
        if (!RegexUtils.DOCUMENT_PATTERN.matcher(document).matches()) {
            throw new InvalidDocumentException();
        }
    }

    private void validateAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < DomainConstants.MIN_AGE) {
            throw new UnderAgeUserException();
        }
    }

    private void checkIfDocumentAlreadyExists(String identityDocument) {
        UserModel existingUser = userPersistencePort.getUserByDocument(identityDocument);
        if (existingUser != null) {
            throw new DuplicateDocumentException();
        }
    }

    private void checkIfUserAlreadyExists(String email) {
        UserModel existingUser = userPersistencePort.getUserByEmail(email);
        if (existingUser != null) {
            throw new UserAlreadyExistsException();
        }
    }
}