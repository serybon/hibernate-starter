package by.bnd.hibernate.starter.service;

import by.bnd.hibernate.starter.dao.UserRepository;
import by.bnd.hibernate.starter.dto.UserCreateDto;
import by.bnd.hibernate.starter.dto.UserReadDto;
import by.bnd.hibernate.starter.mapper.UserCreateMapper;
import by.bnd.hibernate.starter.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public boolean delete(Long id) {
        var mayBeUser = userRepository.findById(id);
        mayBeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return mayBeUser.isPresent();
    }

    public Optional<UserReadDto> findUserById(Long id) {
        return userRepository.findById(id).map(userReadMapper::mapFrom);
    }

    public Long create(UserCreateDto userCreateDto) {
        //validation
        var userEntity = userCreateMapper.mapFrom(userCreateDto);
        return userRepository.save(userEntity).getId();
    }

    public boolean update(UserCreateDto userCreateDto, Long id) {

        var existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            var userEntity = userCreateMapper.mapFrom(userCreateDto);
            // Устанавливаем ID для обновляемой сущности
            userEntity.setId(id);
            // Обновляем пользователя в репозитории
            userRepository.update(userEntity);
            return true;
        } else {
            // Если пользователь не найден, возвращаем false
            return false;
        }
    }
}
