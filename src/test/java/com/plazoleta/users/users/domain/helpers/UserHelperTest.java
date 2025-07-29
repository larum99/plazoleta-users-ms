package com.plazoleta.users.users.domain.helpers;

import com.plazoleta.users.users.domain.exceptions.*;
import com.plazoleta.users.users.domain.model.RoleModel;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.out.RolePersistencePort;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHelperTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private RolePersistencePort rolePersistencePort;

    private UserModel user;

    @BeforeEach
    void setUp() {
        user = new UserModel();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setIdentityDocument("12345678");
        user.setPhoneNumber("+1234567890");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(new RoleModel(1L, null, null));
    }

    @Test
    void normalizeUser_shouldTrimAllStringFields() {
        user.setFirstName("  John  ");
        user.setLastName("  Doe  ");
        user.setEmail("  john.doe@example.com  ");
        UserHelper.normalizeUser(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void validateMandatoryFields_withNullFirstName_shouldThrowMissingFieldException() {
        user.setFirstName(null);
        assertThrows(MissingFieldException.class, () -> UserHelper.validateMandatoryFields(user));
    }

    @Test
    void validateMandatoryFields_withBlankLastName_shouldThrowMissingFieldException() {
        user.setLastName(" ");
        assertThrows(MissingFieldException.class, () -> UserHelper.validateMandatoryFields(user));
    }

    @Test
    void validateEmail_withInvalidFormat_shouldThrowInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () -> UserHelper.validateEmail("invalid-email"));
    }

    @Test
    void validatePhone_withInvalidFormat_shouldThrowInvalidPhoneException() {
        assertThrows(InvalidPhoneException.class, () -> UserHelper.validatePhone("12345ABC"));
    }

    @Test
    void validateDocument_withInvalidFormat_shouldThrowInvalidDocumentException() {
        assertThrows(InvalidDocumentException.class, () -> UserHelper.validateDocument("123A56"));
    }

    @Test
    void validateAge_whenUserIsUnderAge_shouldThrowUnderAgeUserException() {
        LocalDate underAgeDate = LocalDate.now().minusYears(17);
        assertThrows(UnderAgeUserException.class, () -> UserHelper.validateAge(underAgeDate));
    }

    @Test
    void validateAge_whenUserIsAdult_shouldNotThrowException() {
        LocalDate adultDate = LocalDate.now().minusYears(18);
        assertDoesNotThrow(() -> UserHelper.validateAge(adultDate));
    }

    @Test
    void checkIfDocumentExists_whenDocumentExists_shouldThrowDuplicateDocumentException() {
        when(userPersistencePort.getUserByDocument("12345678")).thenReturn(new UserModel());
        assertThrows(DuplicateDocumentException.class, () -> UserHelper.checkIfDocumentExists("12345678", userPersistencePort));
    }

    @Test
    void checkIfEmailExists_whenEmailExists_shouldThrowUserAlreadyExistsException() {
        when(userPersistencePort.getUserByEmail("test@example.com")).thenReturn(new UserModel());
        assertThrows(UserAlreadyExistsException.class, () -> UserHelper.checkIfEmailExists("test@example.com", userPersistencePort));
    }

    @Test
    void validateAndAssignRole_withValidRole_shouldAssignRole() {
        RoleModel fullRole = new RoleModel(1L, "TEST_ROLE", "A test role");
        when(rolePersistencePort.findById(1L)).thenReturn(fullRole);

        UserHelper.validateAndAssignRole(user, rolePersistencePort);

        assertEquals(fullRole, user.getRole());
        assertEquals("TEST_ROLE", user.getRole().getName());
    }

    @Test
    void validateAndAssignRole_withNullRoleIdObject_shouldThrowMissingFieldException() {
        user.setRole(null);
        assertThrows(MissingFieldException.class, () -> UserHelper.validateAndAssignRole(user, rolePersistencePort));
    }

    @Test
    void validateAndAssignRole_withNullRoleIdField_shouldThrowMissingFieldException() {
        user.setRole(new RoleModel(null, null, null));
        assertThrows(MissingFieldException.class, () -> UserHelper.validateAndAssignRole(user, rolePersistencePort));
    }

    @Test
    void validateAndAssignRole_withInvalidRoleId_shouldThrowInvalidRoleException() {
        when(rolePersistencePort.findById(99L)).thenReturn(null);
        user.setRole(new RoleModel(99L, null, null));
        assertThrows(InvalidRoleException.class, () -> UserHelper.validateAndAssignRole(user, rolePersistencePort));
    }
}