package com.plazoleta.users.users.domain.usecases;

import com.plazoleta.users.users.domain.exceptions.*;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;
import com.plazoleta.users.users.domain.utils.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        userModel.setFirstName("John");
        userModel.setLastName("Doe");
        userModel.setIdentityDocument("123456789");
        userModel.setPhoneNumber("+573001234567");
        userModel.setBirthDate(LocalDate.of(2000, 1, 1));
        userModel.setEmail("john.doe@example.com");
        userModel.setPassword("Password123");
    }

    @Test
    void registerUser_happyPath_shouldSaveUserWithClientRole() {
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(passwordEncoderPort.encode("Password123")).thenReturn("encodedPassword");

        userUseCase.registerUser(userModel, "CLIENTE");

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userPersistencePort).saveUser(userCaptor.capture());
        UserModel savedUser = userCaptor.getValue();

        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(Role.CLIENTE, savedUser.getRole());
        assertEquals("John", savedUser.getFirstName());
    }

    @Test
    void registerUser_withNullRole_shouldSaveUserWithDefaultRolePropietario() {
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPassword");

        userUseCase.registerUser(userModel, null);

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userPersistencePort).saveUser(userCaptor.capture());
        UserModel savedUser = userCaptor.getValue();

        assertEquals(Role.PROPIETARIO, savedUser.getRole());
    }

    @Test
    void registerUser_withRoleContainingWhitespace_shouldTrimAndSetRole() {
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPassword");

        userUseCase.registerUser(userModel, "  PROPIETARIO  ");

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userPersistencePort).saveUser(userCaptor.capture());
        UserModel savedUser = userCaptor.getValue();

        assertEquals(Role.PROPIETARIO, savedUser.getRole());
    }

    @Test
    void registerUser_whenUserIsUnderAge_shouldThrowUnderAgeUserException() {
        userModel.setBirthDate(LocalDate.now().minusYears(17));
        assertThrows(UnderAgeUserException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any(UserModel.class));
    }

    @Test
    void registerUser_whenEmailAlreadyExists_shouldThrowUserAlreadyExistsException() {
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByEmail("john.doe@example.com")).thenReturn(new UserModel());
        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any(UserModel.class));
    }

    @Test
    void registerUser_whenDocumentAlreadyExists_shouldThrowDuplicateDocumentException() {
        when(userPersistencePort.getUserByDocument("123456789")).thenReturn(new UserModel());
        assertThrows(DuplicateDocumentException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any(UserModel.class));
        verify(userPersistencePort, never()).getUserByEmail(anyString());
    }

    @Test
    void registerUser_withInvalidEmailFormat_shouldThrowInvalidEmailException() {
        userModel.setEmail("correo-invalido");
        assertThrows(InvalidEmailException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any(UserModel.class));
    }

    @Test
    void registerUser_withInvalidPhoneFormat_shouldThrowInvalidPhoneException() {
        userModel.setPhoneNumber("300123456A");
        assertThrows(InvalidPhoneException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_withTooLongPhoneNumber_shouldThrowInvalidPhoneException() {
        userModel.setPhoneNumber("+5730012345678901");
        assertThrows(InvalidPhoneException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_withInvalidDocument_shouldThrowInvalidDocumentException() {
        userModel.setIdentityDocument("123A456");
        assertThrows(InvalidDocumentException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any(UserModel.class));
    }

    @Test
    void registerUser_withMissingFirstName_shouldThrowMissingFieldException() {
        userModel.setFirstName(" ");
        assertThrows(MissingFieldException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any(UserModel.class));
    }

    @Test
    void registerUser_withMissingLastName_shouldThrowMissingFieldException() {
        userModel.setLastName(null);
        assertThrows(MissingFieldException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_withMissingBirthDate_shouldThrowMissingFieldException() {
        userModel.setBirthDate(null);
        assertThrows(MissingFieldException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_withMissingPassword_shouldThrowMissingFieldException() {
        userModel.setPassword(null);
        assertThrows(MissingFieldException.class, () -> userUseCase.registerUser(userModel, "CLIENTE"));
        verify(userPersistencePort, never()).saveUser(any());
    }
}