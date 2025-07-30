package com.plazoleta.users.users.domain.ports.in;

import com.plazoleta.users.users.application.dto.request.AuthenticationRequest;
import com.plazoleta.users.users.domain.model.UserModel;

public interface AuthenticationServicePort {
    UserModel authenticate(AuthenticationRequest authenticationRequest);
}