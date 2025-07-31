package com.plazoleta.users.users.domain.helpers;

import com.plazoleta.users.users.domain.exceptions.*;
import com.plazoleta.users.users.domain.model.RoleModel;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.out.RolePersistencePort;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;
import com.plazoleta.users.users.domain.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
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
    private static final String ROLE_ADMIN = "ADMINISTRADOR";
    private static final String ROLE_OWNER = "PROPIETARIO";
    private static final String ROLE_EMPLOYEE = "EMPLEADO";

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
    }

    @Test
    void normalizeUser_shouldTrimAllStringFields() {
        // Arrange
        user.setFirstName("  John  ");
        user.setLastName("  Doe  ");
        user.setEmail("  john.doe@example.com  ");
        user.setIdentityDocument("  112233  ");
        user.setPhoneNumber("  +573004005060  ");
        user.setPassword("  secret  ");

        // Act
        UserHelper.normalizeUser(user);

        // Assert
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("112233", user.getIdentityDocument());
        assertEquals("+573004005060", user.getPhoneNumber());
        assertEquals("secret", user.getPassword());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void validateMandatoryFields_withBlankFirstName_shouldThrowMissingFieldException(String invalidName) {
        user.setFirstName(invalidName);
        assertThrows(MissingFieldException.class, () -> UserHelper.validateMandatoryFields(user));
    }

    @Test
    void validateMandatoryFields_withNullBirthDate_shouldThrowMissingFieldException() {
        user.setBirthDate(null);
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
    void assignOwnerRole_whenRoleExists_shouldAssignRoleToUser() {
        RoleModel ownerRole = new RoleModel(2L, ROLE_OWNER, "Restaurant Owner");
        when(rolePersistencePort.findByName(ROLE_OWNER)).thenReturn(ownerRole);

        UserHelper.assignOwnerRole(user, rolePersistencePort);

        assertNotNull(user.getRole());
        assertEquals(ROLE_OWNER, user.getRole().getName());
    }

    @Test
    void assignOwnerRole_whenRoleNotFound_shouldThrowRoleNotFoundException() {
        when(rolePersistencePort.findByName(ROLE_OWNER)).thenReturn(null);
        assertThrows(RoleNotFoundException.class, () -> UserHelper.assignOwnerRole(user, rolePersistencePort));
    }

    @Test
    void assignEmployeeRole_whenRoleExists_shouldAssignRoleToUser() {
        RoleModel employeeRole = new RoleModel(3L, ROLE_EMPLOYEE, "Restaurant Employee");
        when(rolePersistencePort.findByName(ROLE_EMPLOYEE)).thenReturn(employeeRole);

        UserHelper.assignEmployeeRole(user, rolePersistencePort);

        assertNotNull(user.getRole());
        assertEquals(ROLE_EMPLOYEE, user.getRole().getName());
    }

    @Test
    void assignEmployeeRole_whenRoleNotFound_shouldThrowRoleNotFoundException() {
        when(rolePersistencePort.findByName(ROLE_EMPLOYEE)).thenReturn(null);
        assertThrows(RoleNotFoundException.class, () -> UserHelper.assignEmployeeRole(user, rolePersistencePort));
    }

    @Test
    void validateRoleIsAdmin_withAdminRole_shouldNotThrowException() {
        assertDoesNotThrow(() -> UserHelper.validateRoleIsAdmin(ROLE_ADMIN));
    }

    @Test
    void validateRoleIsAdmin_withNonAdminRole_shouldThrowForbiddenException() {
        assertThrows(ForbiddenException.class, () -> UserHelper.validateRoleIsAdmin(ROLE_OWNER));
    }

    @Test
    void validateRoleIsOwner_withOwnerRole_shouldNotThrowException() {
        assertDoesNotThrow(() -> UserHelper.validateRoleIsOwner(ROLE_OWNER));
    }

    @Test
    void validateRoleIsOwner_withNonOwnerRole_shouldThrowForbiddenException() {
        assertThrows(ForbiddenException.class, () -> UserHelper.validateRoleIsOwner(ROLE_ADMIN));
    }
}