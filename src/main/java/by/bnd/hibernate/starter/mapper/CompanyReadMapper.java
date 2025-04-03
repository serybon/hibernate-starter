package by.bnd.hibernate.starter.mapper;

import by.bnd.hibernate.starter.dto.CompanyReadDto;
import by.bnd.hibernate.starter.entity.Company;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {
    @Override
    public CompanyReadDto mapFrom(Company object) {
        return new CompanyReadDto(object.getId(),object.getName());
    }
}
