package com.plazoleta.users.users.domain.usecases;

import com.plazoleta.users.users.domain.exceptions.DuplicatedDocumentException;
import com.plazoleta.users.users.domain.exceptions.ForbiddenException;
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
    private static final String ROLE_ADMIN = "ADMINISTRADOR";
    private static final String ROLE_OWNER = "PROPIETARIO";
    private static final String ROLE_EMPLOYEE = "EMPLEADO";

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
    void registerUser_asAdmin_shouldCreateOwnerUserSuccessfully() {
        RoleModel ownerRole = new RoleModel(2L, ROLE_OWNER, "Rol para propietarios");
        when(rolePersistencePort.findByName(ROLE_OWNER)).thenReturn(ownerRole);
        when(passwordEncoderPort.encode("Password123")).thenReturn("encodedPassword");
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(null);

        userUseCase.registerOwner(userModel, ROLE_ADMIN);

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userPersistencePort).saveUser(userCaptor.capture());
        UserModel capturedUser = userCaptor.getValue();

        assertEquals("encodedPassword", capturedUser.getPassword());
        assertEquals(ownerRole, capturedUser.getRole());
        assertEquals(ROLE_OWNER, capturedUser.getRole().getName());
        verify(passwordEncoderPort).encode("Password123");
    }

    @Test
    void registerUser_asNonAdmin_shouldThrowForbiddenException() {
        assertThrows(ForbiddenException.class, () -> {
            userUseCase.registerOwner(userModel, ROLE_OWNER);
        });

        verify(userPersistencePort, never()).saveUser(any());
        verify(passwordEncoderPort, never()).encode(anyString());
    }

    @Test
    void createEmployeeByOwner_asOwner_shouldCreateEmployeeSuccessfully() {
        RoleModel employeeRole = new RoleModel(3L, ROLE_EMPLOYEE, "Rol para empleados");
        UserModel employeeModel = userModel;
        Long ownerId = 1L;
        Long restaurantId = 100L;

        when(rolePersistencePort.findByName(ROLE_EMPLOYEE)).thenReturn(employeeRole);
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPasswordForEmployee");
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(null);
        when(userPersistencePort.saveEmployee(any(UserModel.class), eq(restaurantId))).thenReturn(employeeModel);

        userUseCase.createEmployeeByOwner(employeeModel, ownerId, ROLE_OWNER, restaurantId);

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userPersistencePort).saveEmployee(userCaptor.capture(), eq(restaurantId));

        UserModel capturedUser = userCaptor.getValue();

        assertEquals("encodedPasswordForEmployee", capturedUser.getPassword());
        assertEquals(employeeRole, capturedUser.getRole());
        assertEquals(ROLE_EMPLOYEE, capturedUser.getRole().getName());

        verify(userPersistencePort).createEmployee(capturedUser.getId(), restaurantId);
    }


    @Test
    void createEmployeeByOwner_asNonOwner_shouldThrowForbiddenException() {
        Long restaurantId = 100L;
        assertThrows(ForbiddenException.class, () -> {
            userUseCase.createEmployeeByOwner(userModel, 1L, ROLE_ADMIN, restaurantId);
        });

        verify(userPersistencePort, never()).saveEmployee(any(), anyLong());
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

    @Test
    void registerClient_shouldRegisterSuccessfully() {
        RoleModel clientRole = new RoleModel(4L, "CLIENTE", "Rol para clientes");

        when(rolePersistencePort.findByName("CLIENTE")).thenReturn(clientRole);
        when(userPersistencePort.getUserByDocument(anyString())).thenReturn(null);
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(null);
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedClientPassword");

        userUseCase.registerClient(userModel);

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userPersistencePort).saveUser(userCaptor.capture());

        UserModel capturedUser = userCaptor.getValue();
        assertEquals("encodedClientPassword", capturedUser.getPassword());
        assertEquals("CLIENTE", capturedUser.getRole().getName());
        assertNotNull(capturedUser.getRole());
    }

    @Test
    void registerClient_withExistingDocument_shouldThrowDuplicatedDocumentException() {
        when(userPersistencePort.getUserByDocument(userModel.getIdentityDocument())).thenReturn(new UserModel());

        assertThrows(DuplicatedDocumentException.class, () -> userUseCase.registerClient(userModel));
        verify(userPersistencePort, never()).saveUser(any());
    }
}