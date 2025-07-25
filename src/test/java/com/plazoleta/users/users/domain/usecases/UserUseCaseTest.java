package com.plazoleta.users.users.domain.usecases;

import com.plazoleta.users.users.domain.exceptions.*;
import com.plazoleta.users.users.domain.model.RoleModel;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.users.users.domain.ports.out.RolePersistencePort;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserPersistencePort userPersistencePort;
    @Mock
    private PasswordEncoderPort passwordEncoderPort;
    @Mock
    private RolePersistencePort rolePersistencePort;
    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel userModel;
    private RoleModel clientRole;

    @BeforeEach
    void setUp() {
        clientRole = new RoleModel(2L, "CLIENTE", "Rol para clientes");

        userModel = new UserModel();
        userModel.setFirstName("John");
        userModel.setLastName("Doe");
        userModel.setIdentityDocument("123456789");
        userModel.setPhoneNumber("+573001234567");
        userModel.setBirthDate(LocalDate.of(2000, 1, 1));
        userModel.setEmail("john.doe@example.com");
        userModel.setPassword("Password123");
        userModel.setRole(clientRole);
    }

    @Test
    void registerUser_happyPath_withExplicitRole_shouldSaveUser() {
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPassword");
        when(rolePersistencePort.findById(2L)).thenReturn(clientRole);

        userUseCase.registerUser(userModel);

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userPersistencePort).saveUser(userCaptor.capture());
        UserModel savedUser = userCaptor.getValue();

        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(clientRole, savedUser.getRole());
    }

    @Test
    void registerUser_happyPath_withNullRole_shouldAssignPropietarioRole() {
        userModel.setRole(null);
        RoleModel propietarioRole = new RoleModel(1L, "PROPIETARIO", "Rol para propietarios");

        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPassword");
        when(rolePersistencePort.findByName("PROPIETARIO")).thenReturn(propietarioRole);

        userUseCase.registerUser(userModel);

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userPersistencePort).saveUser(userCaptor.capture());
        UserModel savedUser = userCaptor.getValue();

        assertEquals(propietarioRole, savedUser.getRole());
    }

    @Test
    void registerUser_withInvalidRoleId_shouldThrowInvalidRoleException() {
        when(rolePersistencePort.findById(2L)).thenReturn(null);
        assertThrows(InvalidRoleException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_whenDefaultRoleIsNotFound_shouldThrowInvalidRoleException() {
        userModel.setRole(null);
        when(rolePersistencePort.findByName("PROPIETARIO")).thenReturn(null);
        assertThrows(InvalidRoleException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_whenUserIsUnderAge_shouldThrowUnderAgeUserException() {
        userModel.setBirthDate(LocalDate.now().minusYears(17));
        assertThrows(UnderAgeUserException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_whenEmailAlreadyExists_shouldThrowUserAlreadyExistsException() {
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(new UserModel());
        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_whenDocumentAlreadyExists_shouldThrowDuplicateDocumentException() {
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(new UserModel());
        assertThrows(DuplicateDocumentException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_withInvalidEmailFormat_shouldThrowInvalidEmailException() {
        userModel.setEmail("correo-invalido");
        assertThrows(InvalidEmailException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_withInvalidPhoneFormat_shouldThrowInvalidPhoneException() {
        userModel.setPhoneNumber("300123456A");
        assertThrows(InvalidPhoneException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_withInvalidDocument_shouldThrowInvalidDocumentException() {
        userModel.setIdentityDocument("123A456");
        assertThrows(InvalidDocumentException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_withMissingLastName_shouldThrowMissingFieldException() {
        userModel.setLastName(null);
        assertThrows(MissingFieldException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void registerUser_withMissingBirthDate_shouldThrowMissingFieldException() {
        userModel.setBirthDate(null);
        assertThrows(MissingFieldException.class, () -> userUseCase.registerUser(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }
}