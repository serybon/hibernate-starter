package by.bnd.hibernate.starter.dao;

import by.bnd.hibernate.starter.entity.Company;

import javax.persistence.EntityManager;

public class CompanyRepository extends BaseRepository<Integer, Company> {

    public CompanyRepository(EntityManager entityManager) {
        super(entityManager, Company.class);
    }
}
