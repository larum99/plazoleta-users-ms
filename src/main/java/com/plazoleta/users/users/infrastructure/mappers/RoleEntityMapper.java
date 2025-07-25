package com.plazoleta.users.users.infrastructure.mappers;

import com.plazoleta.users.users.domain.model.RoleModel;
import com.plazoleta.users.users.infrastructure.entities.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {
    RoleModel entityToModel(RoleEntity entity);
    RoleEntity modelToEntity(RoleModel model);
}
