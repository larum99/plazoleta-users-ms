package com.plazoleta.users.users.domain.ports.in;

public interface RoleValidatorPort {
    String extractRole(String token);
}

