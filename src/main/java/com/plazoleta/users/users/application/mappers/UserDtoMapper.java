package com.plazoleta.users.users.application.mappers;

import com.plazoleta.users.users.application.dto.request.SaveUserRequest;
import com.plazoleta.users.users.application.dto.response.UserResponse;
import com.plazoleta.users.users.domain.model.UserModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {
    @Mapping(target = "role.id", source = "roleId")
    UserModel requestToModel(SaveUserRequest saveUserRequest);

    @Mapping(source = "role.name", target = "role")
    UserResponse modelToResponse(UserModel userModel);
}
