package by.bnd.hibernate.starter.dto;

import by.bnd.hibernate.starter.entity.PersonalInfo;
import by.bnd.hibernate.starter.entity.Role;

public record UserReadDto(Long id, PersonalInfo personalInfo, String username,
                          Role role, CompanyReadDto company) {
}
