package by.bnd.hibernate.starter.mapper;

import by.bnd.hibernate.starter.dto.UserReadDto;
import by.bnd.hibernate.starter.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto mapFrom(User object) {
        return new UserReadDto(object.getId(),
                object.getPersonalInfo(),
                object.getUsername(),
                object.getRole(),
                companyReadMapper.mapFrom(object.getCompany()));
    }
}
