package com.plazoleta.users.users.domain.usecases;

import com.plazoleta.users.users.application.dto.request.AuthenticationRequest;
import com.plazoleta.users.users.domain.exceptions.InvalidCredentialsException;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationUseCaseTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private AuthenticationUseCase authenticationUseCase;

    private UserModel userModel;
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setEmail("test@example.com");
        userModel.setPassword("encodedPassword");

        authenticationRequest = new AuthenticationRequest("test@example.com", "plainPassword123");
    }

    @Test
    void authenticate_withValidCredentials_shouldReturnUser() {
        when(userPersistencePort.getUserByEmail(authenticationRequest.email())).thenReturn(userModel);
        when(passwordEncoderPort.matches(authenticationRequest.password(), userModel.getPassword())).thenReturn(true);

        UserModel authenticatedUser = authenticationUseCase.authenticate(authenticationRequest);

        assertNotNull(authenticatedUser);
        assertEquals(userModel.getEmail(), authenticatedUser.getEmail());
        assertEquals(userModel.getId(), authenticatedUser.getId());

        verify(userPersistencePort).getUserByEmail("test@example.com");
        verify(passwordEncoderPort).matches("plainPassword123", "encodedPassword");
    }

    @Test
    void authenticate_withNonExistentEmail_shouldThrowInvalidCredentialsException() {
        when(userPersistencePort.getUserByEmail(authenticationRequest.email())).thenReturn(null);

        assertThrows(InvalidCredentialsException.class, () -> {
            authenticationUseCase.authenticate(authenticationRequest);
        });

        verify(userPersistencePort).getUserByEmail("test@example.com");
        verify(passwordEncoderPort, never()).matches(anyString(), anyString());
    }

    @Test
    void authenticate_withIncorrectPassword_shouldThrowInvalidCredentialsException() {
        when(userPersistencePort.getUserByEmail(authenticationRequest.email())).thenReturn(userModel);
        when(passwordEncoderPort.matches(authenticationRequest.password(), userModel.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> {
            authenticationUseCase.authenticate(authenticationRequest);
        });

        verify(userPersistencePort).getUserByEmail("test@example.com");
        verify(passwordEncoderPort).matches("plainPassword123", "encodedPassword");
    }
}