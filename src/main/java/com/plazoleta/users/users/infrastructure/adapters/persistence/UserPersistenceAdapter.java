package com.plazoleta.users.users.infrastructure.adapters.persistence;

import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.domain.ports.out.UserPersistencePort;
import com.plazoleta.users.users.infrastructure.entities.UserEntity;
import com.plazoleta.users.users.infrastructure.mappers.UserEntityMapper;
import com.plazoleta.users.users.infrastructure.repositories.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void saveUser(UserModel userModel) {
        UserEntity entity = userEntityMapper.modelToEntity(userModel);
        userRepository.save(entity);
    }

    @Override
    public UserModel getUserByEmail(String email) {
        Optional<UserEntity> entity = userRepository.findByEmail(email);
        return userEntityMapper.optionalEntityToModel(entity);
    }

    @Override
    public UserModel getUserByDocument(String identityDocument) {
        Optional<UserEntity> entity = userRepository.findByIdentityDocument(identityDocument);
        return userEntityMapper.optionalEntityToModel(entity);
    }
}
