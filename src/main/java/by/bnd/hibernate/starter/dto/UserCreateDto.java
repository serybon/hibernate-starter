package by.bnd.hibernate.starter.dto;

import by.bnd.hibernate.starter.entity.PersonalInfo;
import by.bnd.hibernate.starter.entity.Role;

public record UserCreateDto(PersonalInfo personalInfo, String userName, Role role, Integer companyId) {

}
