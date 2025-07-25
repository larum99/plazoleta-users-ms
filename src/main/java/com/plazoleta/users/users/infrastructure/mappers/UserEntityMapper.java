package com.plazoleta.users.users.infrastructure.mappers;

import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserModel entityToModel(UserEntity userEntity);
    UserEntity modelToEntity(UserModel userModel);

    default UserModel optionalEntityToModel(Optional<UserEntity> optionalEntity) {
        return optionalEntity.orElse(null) == null ? null : entityToModel(optionalEntity.get());
    }
}
