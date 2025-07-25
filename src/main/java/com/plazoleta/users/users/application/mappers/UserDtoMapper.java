package com.plazoleta.users.users.application.mappers;

import com.plazoleta.users.users.application.dto.request.SaveUserRequest;
import com.plazoleta.users.users.application.dto.response.UserResponse;
import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.utils.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {
    @Mapping(target = "role", ignore = true)
    UserModel requestToModel(SaveUserRequest saveUserRequest);

    UserResponse modelToResponse(UserModel userModel);
}
