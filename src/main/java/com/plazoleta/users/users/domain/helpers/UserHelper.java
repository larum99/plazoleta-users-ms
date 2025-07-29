package com.plazoleta.users.users.domain.helpers;

import com.plazoleta.users.users.domain.exceptions.*;
import com.plazoleta.users.users.domain.model.RoleModel;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.out.RolePersistencePort;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;
import com.plazoleta.users.users.domain.utils.DomainConstants;
import com.plazoleta.users.users.domain.utils.RegexUtils;

import java.time.LocalDate;
import java.time.Period;

public class UserHelper {

    public static void normalizeUser(UserModel userModel) {
        userModel.setFirstName(trim(userModel.getFirstName()));
        userModel.setLastName(trim(userModel.getLastName()));
        userModel.setIdentityDocument(trim(userModel.getIdentityDocument()));
        userModel.setPhoneNumber(trim(userModel.getPhoneNumber()));
        userModel.setEmail(trim(userModel.getEmail()));
        userModel.setPassword(trim(userModel.getPassword()));
    }

    public static void validateMandatoryFields(UserModel user) {
        if (isBlank(user.getFirstName())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_FIRSTNAME);
        if (isBlank(user.getLastName())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_LASTNAME);
        if (isBlank(user.getIdentityDocument())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DOCUMENT);
        if (isBlank(user.getPhoneNumber())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_PHONE);
        if (user.getBirthDate() == null) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_BIRTHDATE);
        if (isBlank(user.getEmail())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_EMAIL);
        if (isBlank(user.getPassword())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_PASSWORD);
    }

    public static void validateEmail(String email) {
        if (!RegexUtils.EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    public static void validatePhone(String phone) {
        if (phone.length() > DomainConstants.PHONE_MAX_LENGTH ||
                !RegexUtils.PHONE_PATTERN.matcher(phone).matches()) {
            throw new InvalidPhoneException();
        }
    }

    public static void validateDocument(String document) {
        if (!RegexUtils.DOCUMENT_PATTERN.matcher(document).matches()) {
            throw new InvalidDocumentException();
        }
    }

    public static void validateAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < DomainConstants.MIN_AGE) {
            throw new UnderAgeUserException();
        }
    }

    public static void checkIfDocumentExists(String document, UserPersistencePort userPort) {
        if (userPort.getUserByDocument(document) != null) {
            throw new DuplicateDocumentException();
        }
    }

    public static void checkIfEmailExists(String email, UserPersistencePort userPort) {
        if (userPort.getUserByEmail(email) != null) {
            throw new UserAlreadyExistsException();
        }
    }

    public static void validateAndAssignRole(UserModel userModel, RolePersistencePort rolePort) {
        if (userModel.getRole() == null || userModel.getRole().getId() == null) {
            throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_ROLE_ID);
        }

        RoleModel role = rolePort.findById(userModel.getRole().getId());
        if (role == null) {
            throw new InvalidRoleException();
        }

        userModel.setRole(role);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
