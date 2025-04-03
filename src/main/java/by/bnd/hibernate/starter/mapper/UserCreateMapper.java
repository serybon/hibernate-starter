package by.bnd.hibernate.starter.mapper;

import by.bnd.hibernate.starter.dao.CompanyRepository;
import by.bnd.hibernate.starter.dto.UserCreateDto;
import by.bnd.hibernate.starter.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final CompanyRepository companyRepository;


    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .personalInfo(object.personalInfo())
                .username(object.userName())
                .role(object.role())
                .company(companyRepository.findById(object.companyId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
