package com.plazoleta.users.users.infrastructure.adapters.security;

import com.plazoleta.users.users.domain.ports.in.RoleValidatorPort;
import com.plazoleta.users.users.infrastructure.security.utils.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class RoleValidatorAdapter implements RoleValidatorPort {

    private final JwtUtil jwtUtil;

    public RoleValidatorAdapter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String extractRole(String token) {
        return jwtUtil.extractRole(token);
    }

    @Override
    public Long extractUserId(String token) {
        return jwtUtil.extractId(token);
    }
}
