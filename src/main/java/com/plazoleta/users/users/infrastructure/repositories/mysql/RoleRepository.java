package com.plazoleta.users.users.infrastructure.repositories.mysql;

import com.plazoleta.users.users.infrastructure.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    Optional<RoleEntity> findById(Long id);

}
