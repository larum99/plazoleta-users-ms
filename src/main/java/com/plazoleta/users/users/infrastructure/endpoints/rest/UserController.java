package com.plazoleta.users.users.infrastructure.endpoints.rest;

import com.plazoleta.users.commons.configurations.swagger.docs.UserControllerDocs.CreateUserDocs;
import com.plazoleta.users.users.application.dto.request.SaveUserRequest;
import com.plazoleta.users.users.application.dto.response.SaveUserResponse;
import com.plazoleta.users.users.application.services.UserService;
import com.plazoleta.users.users.infrastructure.utils.constants.ControllerConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG, description = ControllerConstants.TAG_DESCRIPTION)
public class UserController {

    private final UserService userService;

    @CreateUserDocs
    @PostMapping(ControllerConstants.SAVE_PATH)
    public ResponseEntity<SaveUserResponse> saveUser(@RequestBody SaveUserRequest request) {
        SaveUserResponse response = userService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
