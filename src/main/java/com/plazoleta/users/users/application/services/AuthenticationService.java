package com.plazoleta.users.users.application.services;

import com.plazoleta.users.users.application.dto.request.AuthenticationRequest;
import com.plazoleta.users.users.application.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
