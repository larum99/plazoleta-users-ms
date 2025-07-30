package com.plazoleta.users.users.domain.ports.out;

import com.plazoleta.users.users.domain.model.UserModel;

public interface AuthenticationPersistencePort {
    String generateToken(UserModel user);
}

