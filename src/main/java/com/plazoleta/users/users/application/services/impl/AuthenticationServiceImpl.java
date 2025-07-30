package com.plazoleta.users.users.application.services.impl;

import com.plazoleta.users.users.application.dto.request.AuthenticationRequest;
import com.plazoleta.users.users.application.dto.response.AuthenticationResponse;
import com.plazoleta.users.users.application.services.AuthenticationService;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.in.AuthenticationServicePort;
import com.plazoleta.users.users.domain.ports.out.AuthenticationPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationServicePort authenticationServicePort;
    private final AuthenticationPersistencePort authenticationPersistencePort;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        UserModel user = authenticationServicePort.authenticate(authenticationRequest);

        String token = authenticationPersistencePort.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
