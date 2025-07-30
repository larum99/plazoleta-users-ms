package com.plazoleta.users.users.infrastructure.endpoints.rest;

import com.plazoleta.users.commons.configurations.swagger.docs.AuthControllerDocs;
import com.plazoleta.users.users.application.dto.request.AuthenticationRequest;
import com.plazoleta.users.users.application.dto.response.AuthenticationResponse;
import com.plazoleta.users.users.application.services.AuthenticationService;
import com.plazoleta.users.users.infrastructure.utils.constants.ControllerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @AuthControllerDocs.LoginDocs
    @PostMapping(ControllerConstants.LOGIN_PATH)
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }
}
