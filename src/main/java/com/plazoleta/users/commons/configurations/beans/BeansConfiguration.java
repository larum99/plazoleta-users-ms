package com.plazoleta.users.commons.configurations.beans;

import com.plazoleta.users.users.domain.ports.in.UserServicePort;
import com.plazoleta.users.users.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.users.users.domain.ports.out.RolePersistencePort;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;
import com.plazoleta.users.users.domain.usecases.UserUseCase;
import com.plazoleta.users.users.infrastructure.adapters.encoders.PasswordEncoderAdapter;
import com.plazoleta.users.users.infrastructure.adapters.persistence.RolePersistenceAdapter;
import com.plazoleta.users.users.infrastructure.adapters.persistence.UserPersistenceAdapter;
import com.plazoleta.users.users.infrastructure.mappers.RoleEntityMapper;
import com.plazoleta.users.users.infrastructure.mappers.UserEntityMapper;
import com.plazoleta.users.users.infrastructure.repositories.mysql.RoleRepository;
import com.plazoleta.users.users.infrastructure.repositories.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeansConfiguration {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;
    private final RoleEntityMapper roleEntityMapper;

    @Bean
    public UserServicePort userServicePort() {
        return new UserUseCase(
                userPersistencePort(),
                passwordEncoderPort(),
                rolePersistencePort()
        );
    }

    @Bean
    public UserPersistencePort userPersistencePort() {
        return new UserPersistenceAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public RolePersistencePort rolePersistencePort() {
        return new RolePersistenceAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public PasswordEncoderPort passwordEncoderPort() {
        return new PasswordEncoderAdapter();
    }
}
