package com.plazoleta.users.users.infrastructure.endpoints.rest;

import com.plazoleta.users.commons.configurations.swagger.docs.CreateEmployeeDocs.CreateEmployee;
import com.plazoleta.users.commons.configurations.swagger.docs.UserControllerDocs.*;
import com.plazoleta.users.users.application.dto.request.SaveUserRequest;
import com.plazoleta.users.users.application.dto.response.SaveUserResponse;
import com.plazoleta.users.users.application.dto.response.UserResponse;
import com.plazoleta.users.users.application.mappers.UserDtoMapper;
import com.plazoleta.users.users.application.services.UserService;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.in.UserServicePort;
import com.plazoleta.users.users.infrastructure.utils.constants.ControllerConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.plazoleta.users.users.infrastructure.utils.constants.ControllerConstants.*;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Tag(name = TAG, description = TAG_DESCRIPTION)
public class UserController {

    private final UserService userService;
    private final UserServicePort userServicePort;
    private final UserDtoMapper userDtoMapper;

    @CreateUserDocs
    @PostMapping(OWNER_SAVE_PATH)
    @PreAuthorize(ControllerConstants.ROLE_ADMIN)
    public ResponseEntity<SaveUserResponse> saveUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody SaveUserRequest request) {

        String token = authorizationHeader.replace(BEARER_PREFIX, "");
        SaveUserResponse response = userService.saveOwnerByAdmin(request, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetUserByIdDocs
    @GetMapping(GET_BY_ID_PATH)
    public UserResponse getUserById(@PathVariable Long id) {
        UserModel userModel = userServicePort.getUserById(id);
        return userDtoMapper.modelToResponse(userModel);
    }

    @CreateEmployee
    @PostMapping(SAVE_EMPLOYEE_PATH)
    @PreAuthorize(ControllerConstants.ROLE_OWNER)
    public ResponseEntity<SaveUserResponse> saveEmployee(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody SaveUserRequest request) {

        String token = authorizationHeader.replace(BEARER_PREFIX, "");
        SaveUserResponse response = userService.saveEmployeeByOwner(request, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
