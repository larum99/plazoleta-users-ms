package com.plazoleta.users.users.domain.ports.out;

import com.plazoleta.users.users.domain.model.RoleModel;

public interface RolePersistencePort {
    RoleModel findById(Long id);
    RoleModel findByName(String name);
}
