package com.plazoleta.users.users.infrastructure.adapters.persistence;

import com.plazoleta.users.users.domain.model.RoleModel;
import com.plazoleta.users.users.domain.ports.out.RolePersistencePort;
import com.plazoleta.users.users.infrastructure.entities.RoleEntity;
import com.plazoleta.users.users.infrastructure.mappers.RoleEntityMapper;
import com.plazoleta.users.users.infrastructure.repositories.mysql.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RolePersistencePort {

    private final RoleRepository roleRepository;
    private final RoleEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public RoleModel findByName(String name) {
        RoleEntity entity = roleRepository.findByName(name.trim().toUpperCase()).orElse(null);
        return entity != null ? mapper.entityToModel(entity) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public RoleModel findById(Long id) {
        RoleEntity entity = roleRepository.findById(id).orElse(null);
        return entity != null ? mapper.entityToModel(entity) : null;
    }
}
