package com.plazoleta.users.users.domain.usecases;

import com.plazoleta.users.users.domain.exceptions.UserNotFoundException;
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
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
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
    private RoleModel roleModel;

    @BeforeEach
    void setUp() {
        roleModel = new RoleModel(2L, "CLIENTE", "Rol para clientes");

        userModel = new UserModel();
        userModel.setFirstName("John");
        userModel.setLastName("Doe");
        userModel.setIdentityDocument("123456789");
        userModel.setPhoneNumber("+573001234567");
        userModel.setBirthDate(LocalDate.of(2000, 1, 1));
        userModel.setEmail("john.doe@example.com");
        userModel.setPassword("Password123");
        userModel.setRole(new RoleModel(2L, null, null));
    }

    @Test
    void registerUser_happyPath_shouldCallPortsAndSaveUser() {
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(null);
        when(rolePersistencePort.findById(2L)).thenReturn(roleModel);
        when(passwordEncoderPort.encode("Password123")).thenReturn("encodedPassword");

        userUseCase.registerUser(userModel);

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(passwordEncoderPort).encode("Password123");
        verify(userPersistencePort).saveUser(userCaptor.capture());

        UserModel capturedUser = userCaptor.getValue();
        assertEquals("encodedPassword", capturedUser.getPassword());
        assertEquals(roleModel, capturedUser.getRole());
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUser() {
        Long userId = 1L;
        userModel.setId(userId);
        when(userPersistencePort.getUserById(userId)).thenReturn(Optional.of(userModel));

        UserModel foundUser = userUseCase.getUserById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        verify(userPersistencePort).getUserById(userId);
    }

    @Test
    void getUserById_whenUserDoesNotExist_shouldThrowUserNotFoundException() {
        Long userId = 99L;
        when(userPersistencePort.getUserById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.getUserById(userId));
        verify(userPersistencePort).getUserById(userId);
    }
}