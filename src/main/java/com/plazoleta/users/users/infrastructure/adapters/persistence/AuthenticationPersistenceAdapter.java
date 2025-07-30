package com.plazoleta.users.users.infrastructure.adapters.persistence;

import com.plazoleta.users.users.domain.ports.out.AuthenticationPersistencePort;
import com.plazoleta.users.users.infrastructure.security.utils.JwtUtil;
import com.plazoleta.users.users.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationPersistenceAdapter implements AuthenticationPersistencePort {

    private final JwtUtil jwtUtil;

    @Override
    public String generateToken(UserModel user) {
        return jwtUtil.generateToken(user);
    }
}
